package surfstore;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import surfstore.SurfStoreBasic.Empty;
import surfstore.SurfStoreBasic.FileInfo;
import surfstore.SurfStoreBasic.Block;
import surfstore.SurfStoreBasic.WriteResult;
import surfstore.SurfStoreBasic.WriteResult.Result;

import static surfstore.SurfStoreBasic.WriteResult.Result;
import static surfstore.SurfStoreBasic.WriteResult.Result.OLD_VERSION;


public final class Client {
    public static final int BLOCK_SIZE = 4096;

    enum RequestType {
        UPLOAD, DOWNLOAD, DELETE, GET_VERSION
    }

    private static final Map<String, RequestType> REQUEST_MAP;

    static {
        REQUEST_MAP = new HashMap<>();
        REQUEST_MAP.put("upload", RequestType.UPLOAD);
        REQUEST_MAP.put("download", RequestType.DOWNLOAD);
        REQUEST_MAP.put("delete", RequestType.DELETE);
        REQUEST_MAP.put("getversion", RequestType.GET_VERSION);
    }

    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final ManagedChannel metadataChannel;
    private final MetadataStoreGrpc.MetadataStoreBlockingStub metadataStub;

    private final ManagedChannel blockChannel;
    private final BlockStoreGrpc.BlockStoreBlockingStub blockStub;

    private final ConfigReader config;

    public Client(ConfigReader config) {
        this.metadataChannel = ManagedChannelBuilder.forAddress("127.0.0.1",
                config.getMetadataPort(1)).usePlaintext(true).build();
        this.metadataStub = MetadataStoreGrpc.newBlockingStub(metadataChannel);

        this.blockChannel = ManagedChannelBuilder.forAddress("127.0.0.1",
                config.getBlockPort()).usePlaintext(true).build();
        this.blockStub = BlockStoreGrpc.newBlockingStub(blockChannel);
        this.config = config;
    }

    public void shutdown() throws InterruptedException {
        metadataChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        blockChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private static String SHA_hash(byte[] bytes) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(2);
        }
        byte[] hash = digest.digest(bytes);

        return Base64.getEncoder().encodeToString(hash);
    }

    private ArrayList<byte[]> readFile(String filePath) {
        ArrayList<byte[]> ret = new ArrayList<>();

        File file = new File(filePath);
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] buffer = new byte[BLOCK_SIZE], blockBytes;
            int bytesRead;
            boolean readData;
            do {
                // read from file; write to buffer
                bytesRead = in.read(buffer);
                readData = bytesRead > 0;

                if (readData) {
                    // truncate the extra space in the buffer
                    blockBytes = Arrays.copyOf(buffer, bytesRead);
                    ret.add(blockBytes);
                }
            } while (readData);
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        return ret;
    }

    private boolean handleUpload(String filePath, String fileName) {
        // get all the necessary data
        ArrayList<byte[]> blocksRead = readFile(filePath);

        ArrayList<String> hashes = genHashes(blocksRead);

        HashMap<String, byte[]> hashToBytes = populateHashMap(hashes, blocksRead);

        // set up an object for querying
        FileInfo queryFile = FileInfo.newBuilder().setFilename(fileName).build();

        // keep updating until the server responds with no missing blocks
        boolean update;
        do {
            FileInfo readFile = metadataStub.readFile(queryFile);
            int ver = readFile.getVersion();
            FileInfo modifyFileInfo = genFileInfo(fileName, ver + 1, hashes);
            WriteResult writeResult = metadataStub.modifyFile(modifyFileInfo);
            update = writeResult.getResult() != Result.OK;

            // if missing block is returned, we need to upload additional blocks
            if (update) {
                List<String> missingHashes = writeResult
                        .getMissingBlocksList();
                for (String missingHash : missingHashes) {
                    // get the actual block data using the hash to the block
                    byte[] missingBlock = hashToBytes.get(missingHash);

                    Block block = Block.newBuilder()
                            .setHash(missingHash)
                            .setData(ByteString.copyFrom(missingBlock))
                            .build();
                    blockStub.storeBlock(block);
                }
            }
        } while (update);

        return true;
    }

    private boolean handleDownload(String filePath, String fileName) {
        // set up an object for querying
        FileInfo queryFileInfo = FileInfo.newBuilder().setFilename(fileName).build();

        FileInfo readFile = metadataStub.readFile(queryFileInfo);
        int ver = readFile.getVersion();
        List<String> hashes = readFile.getBlocklistList();

        // if the file does not exist or has been deleted
        if (ver == 0 || ((hashes.size() == 1) && (hashes.get(0).equals("0"))))
            return false;

        // otherwise the file was found on the server
        // get the hashes of blocks needed

        ArrayList<byte[]> fileBlocks = new ArrayList<>();
        for (String blockHash : hashes) {
            // now we need to request the blocks
            Block queryBlock = Block.newBuilder().setHash(blockHash).build();

            // if somehow the Blockstore doesn't the requested block
            if (!blockStub.hasBlock(queryBlock).getAnswer()) {
                return false;
            }

            byte[] receivedBlock = blockStub.getBlock(queryBlock)
                    .getData().toByteArray();
            fileBlocks.add(receivedBlock);
        }

        return constructFile(fileBlocks, filePath);
    }

    private boolean constructFile(ArrayList<byte[]> fileBlocks, String filePath) {
        try {
            FileOutputStream stream = new FileOutputStream(filePath);
            for (byte[] fileBlock : fileBlocks)
                stream.write(fileBlock);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean handleDelete(String fileName) {
        // set up an object for querying
        FileInfo.Builder queryFileInfoBuilder = FileInfo.newBuilder()
                .setFilename(fileName);

        FileInfo readFile = metadataStub.readFile(queryFileInfoBuilder.build());
        int ver = readFile.getVersion();
        List<String> hashes = readFile.getBlocklistList();

        // if the file does not exist or has been deleted
        if (ver == 0 || (hashes.size() == 1 && hashes.get(0).equals("0")))
            return false;

        Result res;
        do {
            ver = readFile.getVersion();
            res = metadataStub.deleteFile(queryFileInfoBuilder
                    .setVersion(ver + 1).build()).getResult();
        } while (res == OLD_VERSION);

        return true;
    }

    private boolean handleGetVersion(String fileName) {
        // set up an object for querying
        FileInfo queryFileInfo = FileInfo.newBuilder().setFilename(fileName).build();

        FileInfo readFile = metadataStub.getVersion(queryFileInfo);

        int version = readFile.getVersion();
        System.out.println(version);
        return true;
    }

    private HashMap<String, byte[]> populateHashMap(ArrayList<String> hashes,
                                                    ArrayList<byte[]> blocksRead) {
        HashMap<String, byte[]> hashToBytes = new HashMap<>();
        int numBlocks = blocksRead.size();
        for (int i = 0; i < numBlocks; i++)
            hashToBytes.put(hashes.get(i), blocksRead.get(i));
        return hashToBytes;
    }

    private ArrayList<String> genHashes(ArrayList<byte[]> blocksRead) {
        ArrayList<String> hashes = new ArrayList<>();
        for (byte[] block : blocksRead) {
            String hash = SHA_hash(block);
            hashes.add(hash);
        }

        return hashes;
    }

    private FileInfo genFileInfo(String fileName, int ver,
                                 ArrayList<String> hashes) {
        return FileInfo.newBuilder()
                .setFilename(fileName)
                .setVersion(ver)
                .addAllBlocklist(hashes).build();
    }


    private String go(Namespace c_args) {
        String requestType = c_args.getString("requestType");
        String fileName, filePath;
        List<String> additionalArgs = c_args.getList("additionalArgs");

        boolean result;
        switch (REQUEST_MAP.get(requestType)) {
            case UPLOAD:
                filePath = additionalArgs.get(0);
                fileName = parseFileName(filePath);
                result = fileName != null && handleUpload(filePath, fileName);
                return result ? "OK" : "Not Found";
            case DOWNLOAD:
                filePath = additionalArgs.get(1);
                fileName = additionalArgs.get(0);
                result = handleDownload(filePath, fileName);
                return result ? "OK" : "Not Found";
            case DELETE:
                fileName = additionalArgs.get(0);
                result = fileName != null && handleDelete(fileName);
                return result ? "OK" : "Not Found";
            case GET_VERSION:
                fileName = additionalArgs.get(0);
                result = handleGetVersion(fileName);
                return result ? "" : "Not Found";
            default:
                System.out.println("panic! requestType is " + requestType);
        }

        return "";
    }

    private String parseFileName(String filePath) {
        File file = new File(filePath);
        return file.exists() ? file.getName() : null;
    }

    private static Namespace parseArgs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Client").build()
                .description("Client for SurfStore");
        parser.addArgument("config_file").type(String.class)
                .help("Path to configuration file");
        parser.addArgument("requestType").type(String.class)
                .help("Valid types: upload/download/delete/getversion")
                .choices("upload", "download", "delete", "getversion");
        parser.addArgument("additionalArgs").type(String.class)
                .help("additionalArgs").nargs("+");

        Namespace res = null;
        try {
            res = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        Namespace c_args = parseArgs(args);
        if (c_args == null) {
            throw new RuntimeException("Argument parsing failed");
        }

        File configf = new File(c_args.getString("config_file"));
        ConfigReader config = new ConfigReader(configf);

        Client client = new Client(config);

        try {
            String result = client.go(c_args);
            if (!result.equals(""))
                System.out.println(result);
        } finally {
            client.shutdown();
        }
    }
}

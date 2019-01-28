package surfstore;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import surfstore.SurfStoreBasic.Empty;
import surfstore.SurfStoreBasic.FileInfo;
import surfstore.SurfStoreBasic.Block;
import surfstore.SurfStoreBasic.WriteResult;
import surfstore.SurfStoreBasic.SimpleAnswer;

import static surfstore.SurfStoreBasic.WriteResult.Result.OK;
import static surfstore.SurfStoreBasic.WriteResult.Result.OLD_VERSION;
import static surfstore.SurfStoreBasic.WriteResult.Result.MISSING_BLOCKS;
import static surfstore.SurfStoreBasic.WriteResult.Result.NOT_LEADER;

public final class BlockStore {
    private static final Logger logger = Logger.getLogger(BlockStore.class.getName());

    public Server server;
    public ConfigReader config;

    public BlockStore(ConfigReader config) {
        this.config = config;
    }

    private void start(int port, int numThreads) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new BlockStoreImpl())
                .executor(Executors.newFixedThreadPool(numThreads))
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is " +
                        "shutting down");
                BlockStore.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private static Namespace parseArgs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("BlockStore").build()
                .description("BlockStore server for SurfStore");
        parser.addArgument("config_file").type(String.class)
                .help("Path to configuration file");
        parser.addArgument("-t", "--followerThreads").type(Integer.class)
                .setDefault(10).help("Maximum number of concurrent followerThreads");

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

        final BlockStore server = new BlockStore(config);
        server.start(config.getBlockPort(), c_args.getInt("followerThreads"));
        server.blockUntilShutdown();
    }

    static class BlockStoreImpl extends BlockStoreGrpc.BlockStoreImplBase {
        // each entry is of the form (hash: byte[])
        HashMap<String, byte[]> blockMap;

        BlockStoreImpl() {
            blockMap = new HashMap<>();
        }

        @Override
        public void ping(Empty req, final StreamObserver<Empty> responseObserver) {
            Empty response = Empty.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void storeBlock(Block request,
                               StreamObserver<Empty> responseObserver) {
            // extract the hash from the request
            String hash = request.getHash();
            byte[] bytes = request.getData().toByteArray();

            blockMap.put(hash, bytes);

            Empty response = Empty.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        // pre-condition: block exists
        public void getBlock(Block request,
                             StreamObserver<Block> responseObserver) {

            // extract the hash from the request
            String hash = request.getHash();

            Block.Builder builder = Block.newBuilder();
            Block response = builder
                    .setHash(hash)
                    .setData(ByteString.copyFrom(
                            blockMap.getOrDefault(hash, null)))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void hasBlock(Block block,
                             StreamObserver<SimpleAnswer> responseObserver) {
            // extract the hash from the request
            String hash = block.getHash();

            SimpleAnswer.Builder builder = SimpleAnswer.newBuilder();
            SimpleAnswer response = builder.setAnswer(blockMap.containsKey(hash)).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}
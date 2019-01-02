package surfstore;

import java.io.File;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import surfstore.SurfStoreBasic.Empty;


public final class Client
{
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final ManagedChannel metadataChannel;
    private final MetadataStoreGrpc.MetadataStoreBlockingStub metadataStub;

    private final ManagedChannel blockChannel;
    private final BlockStoreGrpc.BlockStoreBlockingStub blockStub;

    private final ConfigReader config;

    public Client(ConfigReader config)
    {
        this.metadataChannel = ManagedChannelBuilder.forAddress("127.0.0.1", config.getMetadataPort(1))
                .usePlaintext(true).build();
        this.metadataStub = MetadataStoreGrpc.newBlockingStub(metadataChannel);

        this.blockChannel = ManagedChannelBuilder.forAddress("127.0.0.1", config.getBlockPort())
                .usePlaintext(true).build();
        this.blockStub = BlockStoreGrpc.newBlockingStub(blockChannel);

        this.config = config;
    }

    public void shutdown() throws InterruptedException
    {
        metadataChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        blockChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private void go()
    {
        metadataStub.ping(Empty.newBuilder().build());
        logger.info("Successfully pinged the Metadata server");

        blockStub.ping(Empty.newBuilder().build());
        logger.info("Successfully pinged the Blockstore server");

        // TODO: Implement your client here
    }

    /*
     * TODO: Add command line handling here
     */
    private static Namespace parseArgs(String[] args)
    {
        ArgumentParser parser = ArgumentParsers.newFor("Client").build()
                .description("Client for SurfStore");
        parser.addArgument("config_file").type(String.class)
                .help("Path to configuration file");
        parser.addArgument("requestType").type(String.class)
                .help("upload/download/delete/getversion");
        parser.addArgument("filePath").type(String.class)
                .help("Path to the file");


        Namespace res = null;
        try
        {
            res = parser.parseArgs(args);
        } catch (ArgumentParserException e)
        {
            parser.handleError(e);
        }
        return res;
    }

    public static void main(String[] args) throws Exception
    {
        Namespace c_args = parseArgs(args);
        if (c_args == null)
        {
            throw new RuntimeException("Argument parsing failed");
        }

        String s = c_args.getString("requestType");
        String s2 = c_args.getString("filePath");
        System.out.println("type: " + s);
        System.out.println("filePath: " + s2);



        File configf = new File(c_args.getString("config_file"));
        ConfigReader config = new ConfigReader(configf);

        Client client = new Client(config);

        try
        {
            client.go();
        } finally
        {
            client.shutdown();
        }
    }

}

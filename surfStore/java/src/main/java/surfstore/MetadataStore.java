package surfstore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

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
import surfstore.SurfStoreBasic.LogEntry;
import surfstore.SurfStoreBasic.Block;
import surfstore.SurfStoreBasic.WriteResult;
import surfstore.SurfStoreBasic.SimpleAnswer;
import surfstore.SurfStoreBasic.ModifyRequest;
import surfstore.SurfStoreBasic.ModifyResult;
import surfstore.SurfStoreBasic.LogEntries;

import surfstore.MetadataStoreGrpc.MetadataStoreBlockingStub;
import surfstore.BlockStoreGrpc.BlockStoreBlockingStub;

import static surfstore.SurfStoreBasic.WriteResult.Result.*;

public final class MetadataStore {
    public final Logger logger = Logger.getLogger(
            MetadataStore.class.getName());

    public BlockStoreBlockingStub blockStub;
    public MetadataStoreBlockingStub leaderStub;
    public List<MetadataStoreBlockingStub> followerStubs;

    public Server server;
    public boolean isLeader;

    public MetadataStore(ConfigReader config, Integer currNum) {
        isLeader = currNum.equals(config.leaderNum);

        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1",
                config.getBlockPort()).usePlaintext(true).build();
        blockStub = BlockStoreGrpc.newBlockingStub(channel);

        if (!isLeader) {
            channel = ManagedChannelBuilder.forAddress("127.0.0.1",
                    config.getMetadataPort(config.leaderNum)).usePlaintext(true)
                    .build();
            leaderStub = MetadataStoreGrpc.newBlockingStub(channel);
        } else {
            followerStubs = new ArrayList<>();
            for (Integer num : config.metadataPorts.keySet()) {
                if (!num.equals(config.leaderNum)) {
                    channel = ManagedChannelBuilder.forAddress("127.0.0.1",
                            config.getMetadataPort(num)).usePlaintext(true).build();
                    followerStubs.add(MetadataStoreGrpc.newBlockingStub(channel));
                }
            }
        }
    }

    private void start(int port, int numThreads) throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new MetadataStoreImpl(
                        isLeader, blockStub, leaderStub, followerStubs))
                .executor(Executors.newFixedThreadPool(numThreads))
                .build()
                .start();

        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since " +
                        "JVM is shutting down");
                MetadataStore.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null)
            server.shutdown();
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null)
            server.awaitTermination();
    }

    private static Namespace parseArgs(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("MetadataStore").build()
                .description("MetadataStore server for SurfStore");
        parser.addArgument("config_file").type(String.class)
                .help("Path to configuration file");
        parser.addArgument("-n", "--number").type(Integer.class)
                .setDefault(1).help("Set which number this server is");
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

        if (c_args.getInt("number") > config.getNumMetadataServers()) {
            throw new RuntimeException(String.format("metadata%d not in config file",
                    c_args.getInt("number")));
        }

        final MetadataStore server = new MetadataStore(config,
                c_args.getInt("number"));
        server.start(config.getMetadataPort(c_args.getInt("number")), c_args
                .getInt("followerThreads"));
        server.blockUntilShutdown();
    }

    static class MetadataStoreImpl extends MetadataStoreGrpc.MetadataStoreImplBase {
        // key is the filename
        HashMap<String, Integer> verMap;
        HashMap<String, List<String>> blockListMap;
        boolean crashed;
        boolean isLeader;
        BlockStoreBlockingStub blockStub;
        MetadataStoreBlockingStub leaderStub;
        ReentrantLock leaderLock;
        List<MetadataStoreBlockingStub> followerStubs;
        List<Thread> followerThreads;
        List<Integer> followerVersions;

        List<LogEntry> committedLogs;
        Map<Integer, LogEntry> pendingLogs;

        int nextOpID = 0;

        MetadataStoreImpl(boolean isLeader, BlockStoreBlockingStub blockStub,
                          MetadataStoreBlockingStub leaderStub,
                          List<MetadataStoreBlockingStub> followerStubs) {
            verMap = new HashMap<>();
            blockListMap = new HashMap<>();
            crashed = false;
            this.isLeader = isLeader;
            this.leaderStub = leaderStub;
            this.blockStub = blockStub;
            this.followerStubs = followerStubs;
            this.committedLogs = new ArrayList<>();
            this.pendingLogs = new HashMap<>();


            if (isLeader) {
                leaderLock = new ReentrantLock();
                followerThreads = new ArrayList<>(followerStubs.size());
                followerVersions = new ArrayList<>(followerStubs.size());

                for (int i = 0; i < followerStubs.size(); i++) {
                    followerThreads.add(null);
                    followerVersions.add(0);
                }
            }
        }

        private int genNextID() {
            leaderLock.lock();
            int ret = nextOpID++;
            leaderLock.unlock();
            return ret;
        }

        @Override
        public void ping(Empty req, final StreamObserver<Empty> responseObserver) {
            Empty response = Empty.newBuilder().build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void readFile(FileInfo request,
                             StreamObserver<FileInfo> responseObserver) {
            String fileName = request.getFilename();

            List<String> hashes = blockListMap.getOrDefault(fileName,
                    new ArrayList<>());
            int version = verMap.getOrDefault(fileName, 0);

            FileInfo response = FileInfo.newBuilder()
                    .setFilename(fileName)
                    .setVersion(version)
                    .addAllBlocklist(hashes)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void modifyFile(FileInfo request,
                               StreamObserver<WriteResult> responseObserver) {
            WriteResult.Result responseFlag;
            String fileName = request.getFilename();
            List<String> allHashes = request.getBlocklistList();
            WriteResult.Builder builder = WriteResult.newBuilder();

            if (!isLeader)
                responseFlag = NOT_LEADER;
            else {
                // determine whether the client needs to update additional files
                List<String> missingHashes = genMissingHashes(allHashes);

                int fileVersion = verMap.getOrDefault(fileName, 0);
                boolean correctVersion = request.getVersion() == (fileVersion + 1);
                boolean hasAllBlocks = missingHashes.isEmpty();

                if (correctVersion && hasAllBlocks) {
                    int opID = genNextID();

                    LogEntry logEntry = genLogEntry(opID, LogEntry
                                    .Operation.MODIFY, committedLogs.size(),
                            fileName, fileVersion + 1, allHashes);

                    pendingLogs.put(opID, logEntry);

                    boolean majority = majorityYes(logEntry);
                    if (majority)
                        commitUpdate(logEntry);
                    else
                        cancelUpdate(logEntry);

                    pendingLogs.remove(opID);
                }

                responseFlag = !correctVersion ? OLD_VERSION :
                        hasAllBlocks ? OK : MISSING_BLOCKS;

                builder.addAllMissingBlocks(missingHashes).setCurrentVersion(
                        verMap.getOrDefault(fileName, 0));
            }

            WriteResult response = builder
                    .setResult(responseFlag)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        private LogEntry genLogEntry(int entryIdentifier, LogEntry.Operation op,
                                     int opIndex, String fileName, int fileVersion,
                                     List<String> allHashes) {
            return LogEntry.newBuilder()
                    .setEntryID(entryIdentifier)
                    .setOp(op)
                    .setOpIndex(opIndex)
                    .setFilename(fileName)
                    .setVersion(fileVersion)
                    .addAllHashes(allHashes)
                    .build();
        }


        private void commitUpdate(LogEntry logEntry) {
            committedLogs.add(logEntry);

            // actually commit
            String fileName = logEntry.getFilename();
            int fileVersion = logEntry.getVersion();
            List<String> allHashes = logEntry.getHashesList();

            blockListMap.put(fileName, allHashes);
            verMap.put(fileName, fileVersion);

            int targetVersion = committedLogs.size();

            notifyFollowers(targetVersion);
        }

        private void cancelUpdate(LogEntry logEntry) {
            for (int i = 0; i < followerStubs.size(); i++) {
                MetadataStoreBlockingStub stub = followerStubs.get(i);
                ModifyResult modifyResult = stub.dropLog(logEntry);
                int followerVersion = modifyResult.getCurrentVersion();
                updateFollowerVersion(i, followerVersion);
            }
        }


        private void notifyFollowers(int targetVersion) {
            // spawn followerThreads to notify followers
            for (int i = 0; i < followerStubs.size(); i++) {
                Thread thread = followerThreads.get(i);
                MetadataStoreBlockingStub stub = followerStubs.get(i);

                // stop running followerThreads if necessary
                stopRunningThread(thread);

                LogEntries logEntries = genLogEntries(i, targetVersion);

                Runnable updateRunnable = new UpdateLogRunnable(
                        stub, logEntries,
                        targetVersion, followerVersions, i);
                followerThreads.set(i, new Thread(updateRunnable));
                followerThreads.get(i).start();
            }
        }

        private void stopRunningThread(Thread thread) {
            if (thread != null) {
                try {
                    thread.interrupt();
                    thread.join();
                } catch (Exception ex) {
                }
            }
        }

        private List<String> genMissingHashes(List<String> allHashes) {
            ArrayList<String> missingHashes = new ArrayList<>();
            for (String hash : allHashes) {
                Block block = Block.newBuilder().setHash(hash).build();

                boolean hasBlock = blockStub.hasBlock(block)
                        .getAnswer();

                if (!hasBlock)
                    missingHashes.add(hash);
            }

            return missingHashes;
        }

        private boolean majorityYes(LogEntry logEntry) {
            // leader has one vote automatically
            double approvals = 1;

            // spawn followerThreads to notify followers
            for (int i = 0; i < followerStubs.size(); i++) {
                MetadataStoreBlockingStub stub = followerStubs.get(i);
                ModifyResult modifyResult = stub.updateLog(logEntry);
                int followerVersion = modifyResult.getCurrentVersion();
                boolean followerReady = modifyResult.getReady();
                updateFollowerVersion(i, followerVersion);
                if (followerReady)
                    approvals++;
            }


            return approvals / (followerStubs.size() + 1) > .5;
        }

        private void updateFollowerVersion(int i, int followerVersion) {
            if (followerVersion > followerVersions.get(i))
                followerVersions.set(i, followerVersion);
        }

        private LogEntries genLogEntries(int followerIndex, int targetVersion) {
            // start is inclusive, end is exclusive
            int startIndex = followerVersions.get(followerIndex);
            int endIndex = committedLogs.size();

            if (startIndex >= endIndex)
                return null;

            List<LogEntry> logEntries = committedLogs.subList(startIndex, endIndex);

            return LogEntries.newBuilder().
                    addAllLogEntries(logEntries)
                    .setStartOpIndex(startIndex)
                    .setTargetVersion(targetVersion)
                    .build();
        }

        @Override
        public void deleteFile(FileInfo request,
                               StreamObserver<WriteResult> responseObserver) {
            String fileName = request.getFilename();
            WriteResult.Result responseFlag;

            if (!isLeader)
                responseFlag = NOT_LEADER;
            else {
                if (verMap.containsKey(fileName)) {
                    if (verMap.get(fileName) + 1 != request.getVersion())
                        responseFlag = OLD_VERSION;
                    else {
                        // ready to delete
                        // deleted file represented by ["0"]
                        int entryID = genNextID();
                        int fileVersion = verMap.getOrDefault(fileName, 0);
                        ArrayList<String> allHashes = new ArrayList<>();
                        allHashes.add("0");

                        LogEntry logEntry = genLogEntry(entryID,
                                LogEntry.Operation.DELETE, committedLogs.size(),
                                fileName, fileVersion + 1, allHashes);

                        pendingLogs.put(entryID, logEntry);

                        boolean majority = majorityYes(logEntry);
                        if (majority)
                            commitUpdate(logEntry);
                        else
                            cancelUpdate(logEntry);

                        pendingLogs.remove(entryID);

                        // assume voting always passes, since there's no other flag
                        // available
                        responseFlag = OK;
                    }
                } else
                    responseFlag = MISSING_BLOCKS;
            }

            WriteResult response = WriteResult.newBuilder()
                    .setResult(responseFlag)
                    .setCurrentVersion(verMap.getOrDefault(fileName, 0))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getVersion(FileInfo request,
                               StreamObserver<FileInfo> responseObserver) {
            String fileName = request.getFilename();

            FileInfo response = FileInfo.newBuilder()
                    .setFilename(fileName)
                    .setVersion(verMap.getOrDefault(fileName, 0))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void isLeader(Empty request,
                             StreamObserver<SimpleAnswer> responseObserver) {
            SimpleAnswer response = SimpleAnswer.newBuilder()
                    .setAnswer(isLeader)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void crash(Empty request,
                          StreamObserver<Empty> responseObserver) {
            crashed = true;

            Empty response = Empty.newBuilder()
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void restore(Empty request,
                            StreamObserver<Empty> responseObserver) {
            crashed = false;

            Empty response = Empty.newBuilder().build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void isCrashed(Empty request,
                              StreamObserver<SimpleAnswer> responseObserver) {
            SimpleAnswer response = SimpleAnswer
                    .newBuilder().setAnswer(crashed).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        // assumption: only called to a follower
        public void updateLog(LogEntry request,
                              StreamObserver<ModifyResult> responseObserver) {
            ModifyResult.Builder builder = ModifyResult.newBuilder();
            int currentVersion = committedLogs.size();

            builder.setCurrentVersion(currentVersion);

            if (crashed || currentVersion < request.getOpIndex())
                builder.setReady(false);
            else if (currentVersion >= request.getOpIndex()) {
                pendingLogs.put(request.getEntryID(), request);
                builder.setReady(true);
            }
            ModifyResult response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        // assumption: only called to a follower
        public void dropLog(LogEntry request,
                            StreamObserver<ModifyResult> responseObserver) {
            ModifyResult.Builder builder = ModifyResult.newBuilder();
            int currentVersion = committedLogs.size();

            builder.setCurrentVersion(currentVersion);

            if (!crashed)
                pendingLogs.remove(request.getEntryID());

            ModifyResult response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        // assumption: only called to a follower
        public void commitLog(LogEntries request,
                              StreamObserver<ModifyResult> responseObserver) {
            ModifyResult.Builder builder = ModifyResult.newBuilder();
            int currentVersion = committedLogs.size();

            if (crashed || currentVersion >= request.getTargetVersion())
                builder.setCurrentVersion(committedLogs.size());
            else {
                List<LogEntry> logEntries = request.getLogEntriesList();
                int startIndex = currentVersion - request.getStartOpIndex();

                for (int i = startIndex; i < logEntries.size(); i++)
                    processLogEntry(logEntries.get(i));

                builder.setCurrentVersion(committedLogs.size());
            }

            ModifyResult response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        private void processLogEntry(LogEntry logEntry) {
            String filename = logEntry.getFilename();
            int version = logEntry.getVersion();
            List<String> hashes = logEntry.getHashesList();

            committedLogs.add(logEntry);

            verMap.put(filename, version);
            blockListMap.put(filename, hashes);
        }

        @Override
        public void isReady(ModifyRequest request,
                            StreamObserver<SimpleAnswer> responseObserver) {
            boolean isReady;
            isReady = !crashed && committedLogs.size() >= request.getCurrentVersion();

            SimpleAnswer response = SimpleAnswer
                    .newBuilder().setAnswer(isReady).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }
}

class UpdateLogRunnable implements Runnable {
    private MetadataStoreBlockingStub stub;
    private LogEntries logs;
    private int targetVersion;
    private List<Integer> followerVersions;
    private int followerIndex;

    UpdateLogRunnable(MetadataStoreBlockingStub stub, LogEntries logs,
                      int targetVersion, List<Integer> followerVersions,
                      int followerIndex) {
        this.stub = stub;
        this.logs = logs;
        this.targetVersion = targetVersion;
        this.followerVersions = followerVersions;
        this.followerIndex = followerIndex;
    }

    public void run() {
        // attempt to send the whole thing
        boolean targetReached = false;
        int clientVersion = -1;
        while (!Thread.interrupted() && !targetReached) {
            try {
                // check the updated version
                clientVersion = stub.commitLog(logs).getCurrentVersion();
                targetReached = clientVersion >= targetVersion;
                Thread.sleep(500);
            } catch (Exception ex) {
                return;
            }
        }

        // update was successful
        if (targetReached) {
            if (clientVersion >= followerVersions.get(followerIndex))
                followerVersions.set(followerIndex, clientVersion);
        }
    }
}

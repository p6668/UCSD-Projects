package surfstore;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.6.1)",
    comments = "Source: SurfStoreBasic.proto")
public final class MetadataStoreGrpc {

  private MetadataStoreGrpc() {}

  public static final String SERVICE_NAME = "surfstore.MetadataStore";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Empty,
      surfstore.SurfStoreBasic.Empty> METHOD_PING =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Empty, surfstore.SurfStoreBasic.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "Ping"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.FileInfo,
      surfstore.SurfStoreBasic.FileInfo> METHOD_READ_FILE =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.FileInfo, surfstore.SurfStoreBasic.FileInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "ReadFile"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.FileInfo.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.FileInfo.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.FileInfo,
      surfstore.SurfStoreBasic.WriteResult> METHOD_MODIFY_FILE =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.FileInfo, surfstore.SurfStoreBasic.WriteResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "ModifyFile"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.FileInfo.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.WriteResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.FileInfo,
      surfstore.SurfStoreBasic.WriteResult> METHOD_DELETE_FILE =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.FileInfo, surfstore.SurfStoreBasic.WriteResult>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "DeleteFile"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.FileInfo.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.WriteResult.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Empty,
      surfstore.SurfStoreBasic.SimpleAnswer> METHOD_IS_LEADER =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Empty, surfstore.SurfStoreBasic.SimpleAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "IsLeader"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.SimpleAnswer.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Empty,
      surfstore.SurfStoreBasic.Empty> METHOD_CRASH =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Empty, surfstore.SurfStoreBasic.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "Crash"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Empty,
      surfstore.SurfStoreBasic.Empty> METHOD_RESTORE =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Empty, surfstore.SurfStoreBasic.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "Restore"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Empty,
      surfstore.SurfStoreBasic.SimpleAnswer> METHOD_IS_CRASHED =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Empty, surfstore.SurfStoreBasic.SimpleAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "IsCrashed"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.SimpleAnswer.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.FileInfo,
      surfstore.SurfStoreBasic.FileInfo> METHOD_GET_VERSION =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.FileInfo, surfstore.SurfStoreBasic.FileInfo>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.MetadataStore", "GetVersion"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.FileInfo.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.FileInfo.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MetadataStoreStub newStub(io.grpc.Channel channel) {
    return new MetadataStoreStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MetadataStoreBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MetadataStoreBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MetadataStoreFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MetadataStoreFutureStub(channel);
  }

  /**
   */
  public static abstract class MetadataStoreImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * A simple ping. Does not return anything.
     * Use the status code of the RPC to check for success.
     * </pre>
     */
    public void ping(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_PING, responseObserver);
    }

    /**
     * <pre>
     * Read the requested file.
     * The client only needs to supply the "filename" argument of FileInfo.
     * The server only needs to fill the "version" and "blocklist" fields.
     * If the file does not exist, "version" should be set to 0.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public void readFile(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.FileInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_READ_FILE, responseObserver);
    }

    /**
     * <pre>
     * Write a file.
     * The client must specify all fields of the FileInfo message.
     * The server returns the result of the operation in the "result" field.
     *
     * The server ALWAYS sets "current_version", regardless of whether
     * the command was successful. If the write succeeded, it will be the
     * version number provided by the client. Otherwise, it is set to the
     * version number in the MetadataStore.
     *
     * If the result is MISSING_BLOCKS, "missing_blocks" contains a
     * list of blocks that are not present in the BlockStore.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public void modifyFile(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.WriteResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_MODIFY_FILE, responseObserver);
    }

    /**
     * <pre>
     * Delete a file.
     * This has the same semantics as ModifyFile, except that both the
     * client and server will not specify a blocklist or missing blocks.
     * As in ModifyFile, this call should return an error if the server
     * it is called on isn't the leader
     * </pre>
     */
    public void deleteFile(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.WriteResult> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DELETE_FILE, responseObserver);
    }

    /**
     * <pre>
     * Query whether the MetadataStore server is currently the leader.
     * This call should work even when the server is in a "crashed" state
     * </pre>
     */
    public void isLeader(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_IS_LEADER, responseObserver);
    }

    /**
     * <pre>
     * "Crash" the MetadataStore server.
     * Until Restore() is called, the server should reply to all RPCs
     * with an error (except Restore) and not send any RPCs to other servers.
     * </pre>
     */
    public void crash(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CRASH, responseObserver);
    }

    /**
     * <pre>
     * "Restore" the MetadataStore server, allowing it to start
     * sending and responding to all RPCs once again.
     * </pre>
     */
    public void restore(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_RESTORE, responseObserver);
    }

    /**
     * <pre>
     * Find out if the node is crashed or not
     * (should always work, even if the node is crashed)
     * </pre>
     */
    public void isCrashed(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_IS_CRASHED, responseObserver);
    }

    /**
     * <pre>
     * Returns the current committed version of the requested file
     * The argument's FileInfo only has the "filename" field defined
     * The FileInfo returns the filename and version fields only
     * This should return a result even if the follower is in a
     *   crashed state
     * </pre>
     */
    public void getVersion(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.FileInfo> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_VERSION, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_PING,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Empty,
                surfstore.SurfStoreBasic.Empty>(
                  this, METHODID_PING)))
          .addMethod(
            METHOD_READ_FILE,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.FileInfo,
                surfstore.SurfStoreBasic.FileInfo>(
                  this, METHODID_READ_FILE)))
          .addMethod(
            METHOD_MODIFY_FILE,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.FileInfo,
                surfstore.SurfStoreBasic.WriteResult>(
                  this, METHODID_MODIFY_FILE)))
          .addMethod(
            METHOD_DELETE_FILE,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.FileInfo,
                surfstore.SurfStoreBasic.WriteResult>(
                  this, METHODID_DELETE_FILE)))
          .addMethod(
            METHOD_IS_LEADER,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Empty,
                surfstore.SurfStoreBasic.SimpleAnswer>(
                  this, METHODID_IS_LEADER)))
          .addMethod(
            METHOD_CRASH,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Empty,
                surfstore.SurfStoreBasic.Empty>(
                  this, METHODID_CRASH)))
          .addMethod(
            METHOD_RESTORE,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Empty,
                surfstore.SurfStoreBasic.Empty>(
                  this, METHODID_RESTORE)))
          .addMethod(
            METHOD_IS_CRASHED,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Empty,
                surfstore.SurfStoreBasic.SimpleAnswer>(
                  this, METHODID_IS_CRASHED)))
          .addMethod(
            METHOD_GET_VERSION,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.FileInfo,
                surfstore.SurfStoreBasic.FileInfo>(
                  this, METHODID_GET_VERSION)))
          .build();
    }
  }

  /**
   */
  public static final class MetadataStoreStub extends io.grpc.stub.AbstractStub<MetadataStoreStub> {
    private MetadataStoreStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetadataStoreStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetadataStoreStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetadataStoreStub(channel, callOptions);
    }

    /**
     * <pre>
     * A simple ping. Does not return anything.
     * Use the status code of the RPC to check for success.
     * </pre>
     */
    public void ping(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Read the requested file.
     * The client only needs to supply the "filename" argument of FileInfo.
     * The server only needs to fill the "version" and "blocklist" fields.
     * If the file does not exist, "version" should be set to 0.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public void readFile(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.FileInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_READ_FILE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Write a file.
     * The client must specify all fields of the FileInfo message.
     * The server returns the result of the operation in the "result" field.
     *
     * The server ALWAYS sets "current_version", regardless of whether
     * the command was successful. If the write succeeded, it will be the
     * version number provided by the client. Otherwise, it is set to the
     * version number in the MetadataStore.
     *
     * If the result is MISSING_BLOCKS, "missing_blocks" contains a
     * list of blocks that are not present in the BlockStore.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public void modifyFile(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.WriteResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_MODIFY_FILE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Delete a file.
     * This has the same semantics as ModifyFile, except that both the
     * client and server will not specify a blocklist or missing blocks.
     * As in ModifyFile, this call should return an error if the server
     * it is called on isn't the leader
     * </pre>
     */
    public void deleteFile(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.WriteResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DELETE_FILE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Query whether the MetadataStore server is currently the leader.
     * This call should work even when the server is in a "crashed" state
     * </pre>
     */
    public void isLeader(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_IS_LEADER, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * "Crash" the MetadataStore server.
     * Until Restore() is called, the server should reply to all RPCs
     * with an error (except Restore) and not send any RPCs to other servers.
     * </pre>
     */
    public void crash(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CRASH, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * "Restore" the MetadataStore server, allowing it to start
     * sending and responding to all RPCs once again.
     * </pre>
     */
    public void restore(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_RESTORE, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Find out if the node is crashed or not
     * (should always work, even if the node is crashed)
     * </pre>
     */
    public void isCrashed(surfstore.SurfStoreBasic.Empty request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_IS_CRASHED, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Returns the current committed version of the requested file
     * The argument's FileInfo only has the "filename" field defined
     * The FileInfo returns the filename and version fields only
     * This should return a result even if the follower is in a
     *   crashed state
     * </pre>
     */
    public void getVersion(surfstore.SurfStoreBasic.FileInfo request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.FileInfo> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_VERSION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MetadataStoreBlockingStub extends io.grpc.stub.AbstractStub<MetadataStoreBlockingStub> {
    private MetadataStoreBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetadataStoreBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetadataStoreBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetadataStoreBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * A simple ping. Does not return anything.
     * Use the status code of the RPC to check for success.
     * </pre>
     */
    public surfstore.SurfStoreBasic.Empty ping(surfstore.SurfStoreBasic.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_PING, getCallOptions(), request);
    }

    /**
     * <pre>
     * Read the requested file.
     * The client only needs to supply the "filename" argument of FileInfo.
     * The server only needs to fill the "version" and "blocklist" fields.
     * If the file does not exist, "version" should be set to 0.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public surfstore.SurfStoreBasic.FileInfo readFile(surfstore.SurfStoreBasic.FileInfo request) {
      return blockingUnaryCall(
          getChannel(), METHOD_READ_FILE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Write a file.
     * The client must specify all fields of the FileInfo message.
     * The server returns the result of the operation in the "result" field.
     *
     * The server ALWAYS sets "current_version", regardless of whether
     * the command was successful. If the write succeeded, it will be the
     * version number provided by the client. Otherwise, it is set to the
     * version number in the MetadataStore.
     *
     * If the result is MISSING_BLOCKS, "missing_blocks" contains a
     * list of blocks that are not present in the BlockStore.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public surfstore.SurfStoreBasic.WriteResult modifyFile(surfstore.SurfStoreBasic.FileInfo request) {
      return blockingUnaryCall(
          getChannel(), METHOD_MODIFY_FILE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Delete a file.
     * This has the same semantics as ModifyFile, except that both the
     * client and server will not specify a blocklist or missing blocks.
     * As in ModifyFile, this call should return an error if the server
     * it is called on isn't the leader
     * </pre>
     */
    public surfstore.SurfStoreBasic.WriteResult deleteFile(surfstore.SurfStoreBasic.FileInfo request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DELETE_FILE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Query whether the MetadataStore server is currently the leader.
     * This call should work even when the server is in a "crashed" state
     * </pre>
     */
    public surfstore.SurfStoreBasic.SimpleAnswer isLeader(surfstore.SurfStoreBasic.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_IS_LEADER, getCallOptions(), request);
    }

    /**
     * <pre>
     * "Crash" the MetadataStore server.
     * Until Restore() is called, the server should reply to all RPCs
     * with an error (except Restore) and not send any RPCs to other servers.
     * </pre>
     */
    public surfstore.SurfStoreBasic.Empty crash(surfstore.SurfStoreBasic.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CRASH, getCallOptions(), request);
    }

    /**
     * <pre>
     * "Restore" the MetadataStore server, allowing it to start
     * sending and responding to all RPCs once again.
     * </pre>
     */
    public surfstore.SurfStoreBasic.Empty restore(surfstore.SurfStoreBasic.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_RESTORE, getCallOptions(), request);
    }

    /**
     * <pre>
     * Find out if the node is crashed or not
     * (should always work, even if the node is crashed)
     * </pre>
     */
    public surfstore.SurfStoreBasic.SimpleAnswer isCrashed(surfstore.SurfStoreBasic.Empty request) {
      return blockingUnaryCall(
          getChannel(), METHOD_IS_CRASHED, getCallOptions(), request);
    }

    /**
     * <pre>
     * Returns the current committed version of the requested file
     * The argument's FileInfo only has the "filename" field defined
     * The FileInfo returns the filename and version fields only
     * This should return a result even if the follower is in a
     *   crashed state
     * </pre>
     */
    public surfstore.SurfStoreBasic.FileInfo getVersion(surfstore.SurfStoreBasic.FileInfo request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_VERSION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MetadataStoreFutureStub extends io.grpc.stub.AbstractStub<MetadataStoreFutureStub> {
    private MetadataStoreFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MetadataStoreFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MetadataStoreFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MetadataStoreFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * A simple ping. Does not return anything.
     * Use the status code of the RPC to check for success.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.Empty> ping(
        surfstore.SurfStoreBasic.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_PING, getCallOptions()), request);
    }

    /**
     * <pre>
     * Read the requested file.
     * The client only needs to supply the "filename" argument of FileInfo.
     * The server only needs to fill the "version" and "blocklist" fields.
     * If the file does not exist, "version" should be set to 0.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.FileInfo> readFile(
        surfstore.SurfStoreBasic.FileInfo request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_READ_FILE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Write a file.
     * The client must specify all fields of the FileInfo message.
     * The server returns the result of the operation in the "result" field.
     *
     * The server ALWAYS sets "current_version", regardless of whether
     * the command was successful. If the write succeeded, it will be the
     * version number provided by the client. Otherwise, it is set to the
     * version number in the MetadataStore.
     *
     * If the result is MISSING_BLOCKS, "missing_blocks" contains a
     * list of blocks that are not present in the BlockStore.
     *
     * This command should return an error if it is called on a server
     * that is not the leader
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.WriteResult> modifyFile(
        surfstore.SurfStoreBasic.FileInfo request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_MODIFY_FILE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Delete a file.
     * This has the same semantics as ModifyFile, except that both the
     * client and server will not specify a blocklist or missing blocks.
     * As in ModifyFile, this call should return an error if the server
     * it is called on isn't the leader
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.WriteResult> deleteFile(
        surfstore.SurfStoreBasic.FileInfo request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DELETE_FILE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Query whether the MetadataStore server is currently the leader.
     * This call should work even when the server is in a "crashed" state
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.SimpleAnswer> isLeader(
        surfstore.SurfStoreBasic.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_IS_LEADER, getCallOptions()), request);
    }

    /**
     * <pre>
     * "Crash" the MetadataStore server.
     * Until Restore() is called, the server should reply to all RPCs
     * with an error (except Restore) and not send any RPCs to other servers.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.Empty> crash(
        surfstore.SurfStoreBasic.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CRASH, getCallOptions()), request);
    }

    /**
     * <pre>
     * "Restore" the MetadataStore server, allowing it to start
     * sending and responding to all RPCs once again.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.Empty> restore(
        surfstore.SurfStoreBasic.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_RESTORE, getCallOptions()), request);
    }

    /**
     * <pre>
     * Find out if the node is crashed or not
     * (should always work, even if the node is crashed)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.SimpleAnswer> isCrashed(
        surfstore.SurfStoreBasic.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_IS_CRASHED, getCallOptions()), request);
    }

    /**
     * <pre>
     * Returns the current committed version of the requested file
     * The argument's FileInfo only has the "filename" field defined
     * The FileInfo returns the filename and version fields only
     * This should return a result even if the follower is in a
     *   crashed state
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.FileInfo> getVersion(
        surfstore.SurfStoreBasic.FileInfo request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_VERSION, getCallOptions()), request);
    }
  }

  private static final int METHODID_PING = 0;
  private static final int METHODID_READ_FILE = 1;
  private static final int METHODID_MODIFY_FILE = 2;
  private static final int METHODID_DELETE_FILE = 3;
  private static final int METHODID_IS_LEADER = 4;
  private static final int METHODID_CRASH = 5;
  private static final int METHODID_RESTORE = 6;
  private static final int METHODID_IS_CRASHED = 7;
  private static final int METHODID_GET_VERSION = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MetadataStoreImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MetadataStoreImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PING:
          serviceImpl.ping((surfstore.SurfStoreBasic.Empty) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty>) responseObserver);
          break;
        case METHODID_READ_FILE:
          serviceImpl.readFile((surfstore.SurfStoreBasic.FileInfo) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.FileInfo>) responseObserver);
          break;
        case METHODID_MODIFY_FILE:
          serviceImpl.modifyFile((surfstore.SurfStoreBasic.FileInfo) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.WriteResult>) responseObserver);
          break;
        case METHODID_DELETE_FILE:
          serviceImpl.deleteFile((surfstore.SurfStoreBasic.FileInfo) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.WriteResult>) responseObserver);
          break;
        case METHODID_IS_LEADER:
          serviceImpl.isLeader((surfstore.SurfStoreBasic.Empty) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer>) responseObserver);
          break;
        case METHODID_CRASH:
          serviceImpl.crash((surfstore.SurfStoreBasic.Empty) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty>) responseObserver);
          break;
        case METHODID_RESTORE:
          serviceImpl.restore((surfstore.SurfStoreBasic.Empty) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty>) responseObserver);
          break;
        case METHODID_IS_CRASHED:
          serviceImpl.isCrashed((surfstore.SurfStoreBasic.Empty) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer>) responseObserver);
          break;
        case METHODID_GET_VERSION:
          serviceImpl.getVersion((surfstore.SurfStoreBasic.FileInfo) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.FileInfo>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class MetadataStoreDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return surfstore.SurfStoreBasic.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MetadataStoreGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MetadataStoreDescriptorSupplier())
              .addMethod(METHOD_PING)
              .addMethod(METHOD_READ_FILE)
              .addMethod(METHOD_MODIFY_FILE)
              .addMethod(METHOD_DELETE_FILE)
              .addMethod(METHOD_IS_LEADER)
              .addMethod(METHOD_CRASH)
              .addMethod(METHOD_RESTORE)
              .addMethod(METHOD_IS_CRASHED)
              .addMethod(METHOD_GET_VERSION)
              .build();
        }
      }
    }
    return result;
  }
}

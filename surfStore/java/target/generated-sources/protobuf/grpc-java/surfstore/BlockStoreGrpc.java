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
public final class BlockStoreGrpc {

  private BlockStoreGrpc() {}

  public static final String SERVICE_NAME = "surfstore.BlockStore";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Empty,
      surfstore.SurfStoreBasic.Empty> METHOD_PING =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Empty, surfstore.SurfStoreBasic.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.BlockStore", "Ping"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Block,
      surfstore.SurfStoreBasic.Empty> METHOD_STORE_BLOCK =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Block, surfstore.SurfStoreBasic.Empty>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.BlockStore", "StoreBlock"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Block.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Empty.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Block,
      surfstore.SurfStoreBasic.Block> METHOD_GET_BLOCK =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Block, surfstore.SurfStoreBasic.Block>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.BlockStore", "GetBlock"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Block.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Block.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<surfstore.SurfStoreBasic.Block,
      surfstore.SurfStoreBasic.SimpleAnswer> METHOD_HAS_BLOCK =
      io.grpc.MethodDescriptor.<surfstore.SurfStoreBasic.Block, surfstore.SurfStoreBasic.SimpleAnswer>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "surfstore.BlockStore", "HasBlock"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.Block.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              surfstore.SurfStoreBasic.SimpleAnswer.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BlockStoreStub newStub(io.grpc.Channel channel) {
    return new BlockStoreStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BlockStoreBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new BlockStoreBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BlockStoreFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new BlockStoreFutureStub(channel);
  }

  /**
   */
  public static abstract class BlockStoreImplBase implements io.grpc.BindableService {

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
     * Store the block in storage.
     * The client must fill both fields of the message.
     * </pre>
     */
    public void storeBlock(surfstore.SurfStoreBasic.Block request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_STORE_BLOCK, responseObserver);
    }

    /**
     * <pre>
     * Get a block in storage.
     * The client only needs to supply the "hash" field.
     * The server returns both the "hash" and "data" fields.
     * If the block doesn't exist, "hash" will be the empty string.
     * We will not call this rpc if the block doesn't exist (we'll always
     * call "HasBlock()" first
     * </pre>
     */
    public void getBlock(surfstore.SurfStoreBasic.Block request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Block> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BLOCK, responseObserver);
    }

    /**
     * <pre>
     * Check whether a block is in storage.
     * The client only needs to specify the "hash" field.
     * </pre>
     */
    public void hasBlock(surfstore.SurfStoreBasic.Block request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_HAS_BLOCK, responseObserver);
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
            METHOD_STORE_BLOCK,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Block,
                surfstore.SurfStoreBasic.Empty>(
                  this, METHODID_STORE_BLOCK)))
          .addMethod(
            METHOD_GET_BLOCK,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Block,
                surfstore.SurfStoreBasic.Block>(
                  this, METHODID_GET_BLOCK)))
          .addMethod(
            METHOD_HAS_BLOCK,
            asyncUnaryCall(
              new MethodHandlers<
                surfstore.SurfStoreBasic.Block,
                surfstore.SurfStoreBasic.SimpleAnswer>(
                  this, METHODID_HAS_BLOCK)))
          .build();
    }
  }

  /**
   */
  public static final class BlockStoreStub extends io.grpc.stub.AbstractStub<BlockStoreStub> {
    private BlockStoreStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BlockStoreStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlockStoreStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BlockStoreStub(channel, callOptions);
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
     * Store the block in storage.
     * The client must fill both fields of the message.
     * </pre>
     */
    public void storeBlock(surfstore.SurfStoreBasic.Block request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_STORE_BLOCK, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Get a block in storage.
     * The client only needs to supply the "hash" field.
     * The server returns both the "hash" and "data" fields.
     * If the block doesn't exist, "hash" will be the empty string.
     * We will not call this rpc if the block doesn't exist (we'll always
     * call "HasBlock()" first
     * </pre>
     */
    public void getBlock(surfstore.SurfStoreBasic.Block request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Block> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BLOCK, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Check whether a block is in storage.
     * The client only needs to specify the "hash" field.
     * </pre>
     */
    public void hasBlock(surfstore.SurfStoreBasic.Block request,
        io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_HAS_BLOCK, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BlockStoreBlockingStub extends io.grpc.stub.AbstractStub<BlockStoreBlockingStub> {
    private BlockStoreBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BlockStoreBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlockStoreBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BlockStoreBlockingStub(channel, callOptions);
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
     * Store the block in storage.
     * The client must fill both fields of the message.
     * </pre>
     */
    public surfstore.SurfStoreBasic.Empty storeBlock(surfstore.SurfStoreBasic.Block request) {
      return blockingUnaryCall(
          getChannel(), METHOD_STORE_BLOCK, getCallOptions(), request);
    }

    /**
     * <pre>
     * Get a block in storage.
     * The client only needs to supply the "hash" field.
     * The server returns both the "hash" and "data" fields.
     * If the block doesn't exist, "hash" will be the empty string.
     * We will not call this rpc if the block doesn't exist (we'll always
     * call "HasBlock()" first
     * </pre>
     */
    public surfstore.SurfStoreBasic.Block getBlock(surfstore.SurfStoreBasic.Block request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BLOCK, getCallOptions(), request);
    }

    /**
     * <pre>
     * Check whether a block is in storage.
     * The client only needs to specify the "hash" field.
     * </pre>
     */
    public surfstore.SurfStoreBasic.SimpleAnswer hasBlock(surfstore.SurfStoreBasic.Block request) {
      return blockingUnaryCall(
          getChannel(), METHOD_HAS_BLOCK, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BlockStoreFutureStub extends io.grpc.stub.AbstractStub<BlockStoreFutureStub> {
    private BlockStoreFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private BlockStoreFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlockStoreFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new BlockStoreFutureStub(channel, callOptions);
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
     * Store the block in storage.
     * The client must fill both fields of the message.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.Empty> storeBlock(
        surfstore.SurfStoreBasic.Block request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_STORE_BLOCK, getCallOptions()), request);
    }

    /**
     * <pre>
     * Get a block in storage.
     * The client only needs to supply the "hash" field.
     * The server returns both the "hash" and "data" fields.
     * If the block doesn't exist, "hash" will be the empty string.
     * We will not call this rpc if the block doesn't exist (we'll always
     * call "HasBlock()" first
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.Block> getBlock(
        surfstore.SurfStoreBasic.Block request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BLOCK, getCallOptions()), request);
    }

    /**
     * <pre>
     * Check whether a block is in storage.
     * The client only needs to specify the "hash" field.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<surfstore.SurfStoreBasic.SimpleAnswer> hasBlock(
        surfstore.SurfStoreBasic.Block request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_HAS_BLOCK, getCallOptions()), request);
    }
  }

  private static final int METHODID_PING = 0;
  private static final int METHODID_STORE_BLOCK = 1;
  private static final int METHODID_GET_BLOCK = 2;
  private static final int METHODID_HAS_BLOCK = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BlockStoreImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BlockStoreImplBase serviceImpl, int methodId) {
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
        case METHODID_STORE_BLOCK:
          serviceImpl.storeBlock((surfstore.SurfStoreBasic.Block) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Empty>) responseObserver);
          break;
        case METHODID_GET_BLOCK:
          serviceImpl.getBlock((surfstore.SurfStoreBasic.Block) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.Block>) responseObserver);
          break;
        case METHODID_HAS_BLOCK:
          serviceImpl.hasBlock((surfstore.SurfStoreBasic.Block) request,
              (io.grpc.stub.StreamObserver<surfstore.SurfStoreBasic.SimpleAnswer>) responseObserver);
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

  private static final class BlockStoreDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return surfstore.SurfStoreBasic.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BlockStoreGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BlockStoreDescriptorSupplier())
              .addMethod(METHOD_PING)
              .addMethod(METHOD_STORE_BLOCK)
              .addMethod(METHOD_GET_BLOCK)
              .addMethod(METHOD_HAS_BLOCK)
              .build();
        }
      }
    }
    return result;
  }
}

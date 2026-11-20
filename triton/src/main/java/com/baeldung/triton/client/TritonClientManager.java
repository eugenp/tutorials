package com.baeldung.triton.client;

import inference.GRPCInferenceServiceGrpc;
import inference.GRPCInferenceServiceGrpc.GRPCInferenceServiceBlockingStub;
import inference.GrpcService.ServerLiveRequest;
import inference.GrpcService.ServerLiveResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class TritonClientManager {
    private final ManagedChannel channel;
    private final GRPCInferenceServiceBlockingStub blockingStub;

    public TritonClientManager(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Plaintext because we are querying localhost without TLS
                .build();
        this.blockingStub = GRPCInferenceServiceGrpc.newBlockingStub(channel);
    }

    public GRPCInferenceServiceBlockingStub getStub() {
        return blockingStub;
    }

    public boolean isServerLive() {
        try {
            ServerLiveRequest request = ServerLiveRequest.newBuilder().build();
            ServerLiveResponse response = blockingStub.serverLive(request);
            return response.getLive();
        } catch (Exception e) {
            return false;
        }
    }

    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
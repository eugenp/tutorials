package com.baeldung.grpc.retrypolicy;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.grpc.retry.NotificationRequest;
import com.baeldung.grpc.retry.NotificationResponse;
import com.baeldung.grpc.retry.NotificationServiceGrpc;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcBroadcastingClient {
    private static final Logger logger = LoggerFactory.getLogger(GrpcBroadcastingClient.class);

    protected static Map<String, ?> getServiceConfig() {
        return new Gson().fromJson(new JsonReader(new InputStreamReader(GrpcBroadcastingClient.class.getClassLoader()
            .getResourceAsStream("retry-service-config.json"), StandardCharsets.UTF_8)), Map.class);
    }

    public static NotificationResponse broadcastMessage() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .disableServiceConfigLookUp()
            .defaultServiceConfig(getServiceConfig())
            .enableRetry()
            .build();
        return sendNotification(channel);
    }

    public static NotificationResponse sendNotification(ManagedChannel channel) {
        NotificationServiceGrpc.NotificationServiceBlockingStub notificationServiceStub = NotificationServiceGrpc
            .newBlockingStub(channel);

        NotificationResponse response = notificationServiceStub.notify(NotificationRequest.newBuilder()
            .setType("Warning")
            .setMessage("Heavy rains expected")
            .setMessageID(generateMessageID())
            .build());
        channel.shutdown();
        return response;
    }

    private static int generateMessageID() {
        return new Random().nextInt(90000) + 10000;
    }
}
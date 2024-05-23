package com.baeldung.grpc.retrypolicy;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcBroadcastingServer {
    private static final Logger logger = LoggerFactory.getLogger(GrpcBroadcastingServer.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        Server server = ServerBuilder.forPort(8080)
            .addService(new NotificationServiceImpl())
            .build();
        server.start();
        logger.info("Server started...");
        server.awaitTermination();
    }
}

package com.baeldung.grpc.alts.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Server;
import io.grpc.alts.AltsServerBuilder;

public class AltsOtBookingServer {
    private static final Logger logger = LoggerFactory.getLogger(AltsOtBookingServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        final String CLIENT_SERVICE_ACCOUNT = args[0];
        Server server = AltsServerBuilder.forPort(8080)
            .intercept(new ClientAuthInterceptor(CLIENT_SERVICE_ACCOUNT))
            .addService(new OtBookingService())
            .build();
        server.start();
        logger.info("Booking OT service started successfully!");
        server.awaitTermination();
    }
}

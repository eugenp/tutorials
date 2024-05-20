package com.baeldung.grpc.alts.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.alts.AltsServerBuilder;

public class AltsOtBookingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String CLIENT_SERVICE_ACCOUNT = args[0];
        Server server = AltsServerBuilder.forPort(8080)
            .intercept(new ClientAuthInterceptor(CLIENT_SERVICE_ACCOUNT))
            .addService(new OtBookingService())
            .build();
        server.start();
        server.awaitTermination();
    }
}

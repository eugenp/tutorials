package com.baeldung.grpc.alts.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.alts.AltsServerBuilder;

public class AltsServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = AltsServerBuilder.forPort(8080)
            .addService(new OTBookingService())
            .build();
        server.start();
        server.awaitTermination();
    }

}

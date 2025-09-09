package com.baeldung.grpc.user.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class UserServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
          .addService(new UserServiceImpl())
          .build();

        server.start();
        server.awaitTermination();
    }
}

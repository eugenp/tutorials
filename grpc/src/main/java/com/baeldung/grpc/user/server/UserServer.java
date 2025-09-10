package com.baeldung.grpc.user.server;

import com.baeldung.grpc.user.client.UserClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class UserServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(8080)
          .addService(new UserServiceImpl())
          .build();

        server.start();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        UserClient userClient = new UserClient(channel);
        System.out.println(userClient.getUser(1));
        server.awaitTermination();
    }
}

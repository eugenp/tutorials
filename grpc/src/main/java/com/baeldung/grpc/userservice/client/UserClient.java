package com.baeldung.grpc.userservice.client;

import com.baeldung.grpc.userservice.User;
import com.baeldung.grpc.userservice.UserRequest;
import com.baeldung.grpc.userservice.UserServiceGrpc;
import io.grpc.ManagedChannel;

public class UserClient {
    private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;
    private final ManagedChannel managedChannel;

    public UserClient(ManagedChannel managedChannel) {
        this.managedChannel = managedChannel;
        this.userServiceStub = UserServiceGrpc.newBlockingStub(managedChannel);
    }

    public User getUser(int id) {
        UserRequest userRequest = UserRequest.newBuilder()
          .setId(id)
          .build();

        return userServiceStub.getUser(userRequest).getUser();
    }
}

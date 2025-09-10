package com.baeldung.grpc.user.client;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserClient {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
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

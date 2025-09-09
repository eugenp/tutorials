package com.baeldung.grpc.user.client;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserClient {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
    private final UserServiceGrpc.UserServiceBlockingStub stub;
    private final ManagedChannel channel;

    public UserClient(ManagedChannel channel, UserServiceGrpc.UserServiceBlockingStub stub) {
        this.channel = channel;
        this.stub = stub;
    }

    public User getUser(int id) {
        logger.info("Getting the User from the remote service for id {}", id);

        UserRequest userRequest = UserRequest.newBuilder()
                .setId(id)
                .build();
        try {
            return stub.getUser(userRequest).getUser();
        } catch (StatusRuntimeException ex) {
            logger.error("gRPC failed code: {} status: {}",
                    ex.getStatus().getCode(), ex.getStatus().getDescription());
            throw ex;
        }
    }
}

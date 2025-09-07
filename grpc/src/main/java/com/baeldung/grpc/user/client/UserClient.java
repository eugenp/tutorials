package com.baeldung.grpc.user.client;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserResponse;
import com.baeldung.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserClient {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
    private final UserServiceGrpc.UserServiceBlockingStub stub;
    private final ManagedChannel channel;

    public UserClient(ManagedChannel channel) {
        this.channel = channel;
        this.stub = UserServiceGrpc.newBlockingStub(channel);
    }

    public User getUser(int id) {
        try {
            UserResponse userResponse = stub.getUser(
                    UserRequest.newBuilder().setId(id).build()
            );

            logger.info("Return User from userService for id {}", id);
            return userResponse.getUser();
        } catch (StatusRuntimeException ex) {
            logger.error("RPC failed: " + ex.getStatus().getCode() +
                    " - " + ex.getStatus().getDescription());

            throw ex;
        }
    }

    public void shutdown() {
        channel.shutdown();
    }
}

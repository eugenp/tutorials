package com.baeldung.grpc.user.server;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserResponse;
import com.baeldung.grpc.user.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final Map<Integer, User> usersStore = Map.of(1, User.newBuilder()
      .setId(1)
      .setName("user1")
      .setEmail("user1@example.com")
      .build(), 2, User.newBuilder()
      .setId(2)
      .setName("user2")
      .setEmail("user2@example.com")
      .build());

    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        try {
            User user = Optional.ofNullable(usersStore.get(request.getId()))
              .orElseThrow(() -> new UserNotFoundException(request.getId()));

            UserResponse response = UserResponse.newBuilder()
              .setUser(user)
              .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
            logger.info("Return User for id {}", request.getId());
        } catch (UserNotFoundException ex) {
              responseObserver.onError(Status.NOT_FOUND.withDescription(ex.getMessage()).asRuntimeException());
        }
    }
}


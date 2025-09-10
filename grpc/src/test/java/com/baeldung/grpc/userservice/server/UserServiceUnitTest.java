package com.baeldung.grpc.userservice.server;

import com.baeldung.grpc.userservice.UserRequest;
import com.baeldung.grpc.userservice.UserResponse;
import com.baeldung.grpc.userservice.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceUnitTest {
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @BeforeEach
    void setup() throws IOException {
        String serviceName = InProcessServerBuilder.generateName();

        Server inProcessServer = InProcessServerBuilder.forName(serviceName)
            .directExecutor()
            .addService(new UserServiceImpl())
            .build()
            .start();
        grpcCleanup.register(inProcessServer);

        ManagedChannel managedChannel = InProcessChannelBuilder.forName(serviceName)
            .directExecutor()
            .usePlaintext()
            .build();
        grpcCleanup.register(managedChannel);

        userServiceBlockingStub = UserServiceGrpc.newBlockingStub(managedChannel);
    }

    @Test
    void givenUserIsPresent_whenGetUserIsCalled_ThenReturnUser() {
        UserRequest userRequest = UserRequest.newBuilder()
            .setId(1)
            .build();

        UserResponse userResponse = userServiceBlockingStub.getUser(userRequest);

        assertNotNull(userResponse);
        assertNotNull(userResponse.getUser());
        assertEquals(1, userResponse.getUser().getId());
        assertEquals("user1", userResponse.getUser().getName());
        assertEquals("user1@example.com", userResponse.getUser().getEmail());
    }

    @Test
    void givenUserIsNotPresent_whenGetUserIsCalled_ThenThrowRuntimeException(){
        UserRequest userRequest = UserRequest.newBuilder()
            .setId(3)
            .build();

        StatusRuntimeException statusRuntimeException = assertThrows(StatusRuntimeException.class,
            () -> userServiceBlockingStub.getUser(userRequest));

        assertNotNull(statusRuntimeException);
        assertNotNull(statusRuntimeException.getStatus());
        assertEquals(Status.NOT_FOUND.getCode(), statusRuntimeException.getStatus().getCode());
        assertEquals("User not found with ID 3", statusRuntimeException.getStatus().getDescription());
    }
}
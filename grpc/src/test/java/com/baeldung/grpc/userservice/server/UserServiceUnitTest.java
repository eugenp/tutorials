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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceUnitTest {
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;
    private Server inProcessServer;
    private ManagedChannel managedChannel;

    @BeforeEach
    void setup() throws IOException {
        String serviceName = InProcessServerBuilder.generateName();

        inProcessServer = InProcessServerBuilder.forName(serviceName)
            .directExecutor()
            .addService(new UserServiceImpl())
            .build()
            .start();

        managedChannel = InProcessChannelBuilder.forName(serviceName)
            .directExecutor()
            .usePlaintext()
            .build();

        userServiceBlockingStub = UserServiceGrpc.newBlockingStub(managedChannel);
    }

    @AfterEach
    void tearDown() {
        managedChannel.shutdownNow();
        inProcessServer.shutdownNow();
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
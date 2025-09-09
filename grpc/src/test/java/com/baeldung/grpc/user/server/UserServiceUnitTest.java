package com.baeldung.grpc.user.server;

import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserResponse;
import com.baeldung.grpc.user.UserServiceGrpc;
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

    private Server inProcessServer;
    private ManagedChannel managedChannel;
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @BeforeEach
    public void setup() throws IOException {
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
    public void teardown(){
        managedChannel.shutdown();
        inProcessServer.shutdown();
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
        assertNotNull(statusRuntimeException.getStatus().getDescription());
        assertEquals(Status.NOT_FOUND.getCode(), statusRuntimeException.getStatus().getCode());
        assertTrue(statusRuntimeException.getStatus().getDescription().contains("User not found with ID 3"));
    }
}
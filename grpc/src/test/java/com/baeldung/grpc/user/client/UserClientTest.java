package com.baeldung.grpc.user.client;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.server.UserServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.Server;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserClientTest {

    private static Server inProcessServer;
    private static ManagedChannel channel;
    private static UserClient userClient;

    @BeforeEach
    public void setup() throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        inProcessServer = InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(new UserServiceImpl())
                .build()
                .start();

        channel = InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build();

        userClient = new UserClient(channel);
    }

    @AfterEach
    public void teardown() {
        userClient.shutdown();
        inProcessServer.shutdownNow();
    }

    @Test
    void givenUserIsPresent_whenGetUserIsCalled_ThenReturnUser() {
        User user = userClient.getUser(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("user1", user.getName());
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    void givenUserIsNotPresent_whenGetUserIsCalled_ThenThrowRuntimeException(){

        StatusRuntimeException statusRuntimeException = assertThrows(StatusRuntimeException.class,
                () -> userClient.getUser(1000));

        assertNotNull(statusRuntimeException);
        assertNotNull(statusRuntimeException.getStatus());
        assertNotNull(statusRuntimeException.getStatus().getDescription());
        assertEquals(Status.NOT_FOUND.getCode(), statusRuntimeException.getStatus().getCode());
        assertTrue(statusRuntimeException.getStatus().getDescription().contains("User with ID 1000 not found"));
    }
}

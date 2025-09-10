package com.baeldung.grpc.userservice.client;

import com.baeldung.grpc.userservice.User;
import com.baeldung.grpc.userservice.UserResponse;
import com.baeldung.grpc.userservice.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

public class UserClientUnitTest {
    private UserClient userClient;
    private UserServiceGrpc.UserServiceImplBase mockUserService;
    private Server inProcessServer;
    private ManagedChannel managedChannel;

    @BeforeEach
    public void setup() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        mockUserService = spy(UserServiceGrpc.UserServiceImplBase.class);

        inProcessServer = InProcessServerBuilder
            .forName(serverName)
            .directExecutor()
            .addService(mockUserService)
            .build()
            .start();

        managedChannel = InProcessChannelBuilder.forName(serverName)
            .directExecutor()
            .build();

        userClient = new UserClient(managedChannel);
    }

    @AfterEach
    void tearDown() {
        managedChannel.shutdownNow();
        inProcessServer.shutdownNow();
    }

    @Test
    void givenUserIsPresent_whenGetUserIsCalled_ThenReturnUser() {
        User expectedUser = User.newBuilder()
            .setId(1)
            .setName("user1")
            .setEmail("user1@example.com")
            .build();

        mockGetUser(expectedUser);

        User user = userClient.getUser(1);
        assertEquals(expectedUser, user);
    }

    private void mockGetUser(User expectedUser) {
        Mockito.doAnswer(invocation -> {
            StreamObserver<UserResponse> observer = invocation.getArgument(1);
            UserResponse response = UserResponse.newBuilder()
                .setUser(expectedUser)
                .build();

            observer.onNext(response);
            observer.onCompleted();
            return null;
        }).when(mockUserService).getUser(any(), any());
    }
}

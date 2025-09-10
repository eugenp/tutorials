package com.baeldung.grpc.userservice.client;

import com.baeldung.grpc.userservice.User;
import com.baeldung.grpc.userservice.UserResponse;
import com.baeldung.grpc.userservice.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;

public class UserClientUnitTest {
    private UserClient userClient;
    private UserServiceGrpc.UserServiceImplBase mockUserService;
    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @BeforeEach
    public void setup() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        mockUserService = spy(UserServiceGrpc.UserServiceImplBase.class);

        Server inProcessServer = InProcessServerBuilder
            .forName(serverName)
            .directExecutor()
            .addService(mockUserService)
            .build()
            .start();
        grpcCleanup.register(inProcessServer);

        ManagedChannel managedChannel = InProcessChannelBuilder.forName(serverName)
            .directExecutor()
            .build();
        grpcCleanup.register(managedChannel);

        userClient = new UserClient(managedChannel);
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

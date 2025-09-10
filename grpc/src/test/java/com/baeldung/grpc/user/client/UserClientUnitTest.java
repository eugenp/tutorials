package com.baeldung.grpc.user.client;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserResponse;
import com.baeldung.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
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

    @BeforeEach
    public void setup() throws Exception {
        UserServiceGrpc.UserServiceImplBase mockUserService = spy(UserServiceGrpc.UserServiceImplBase.class);
        Mockito.doAnswer(invocation -> {
            UserRequest request = invocation.getArgument(0);
            StreamObserver<UserResponse> observer = invocation.getArgument(1);

            User user = User.newBuilder()
              .setId(request.getId())
              .setName("user1")
              .setEmail("user1@example.com")
              .build();
            UserResponse response = UserResponse.newBuilder()
             .setUser(user)
             .build();

            observer.onNext(response);
            observer.onCompleted();
            return null;
        }).when(mockUserService).getUser(any(), any());

        String serverName = InProcessServerBuilder.generateName();
        InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(mockUserService.bindService())
                .build()
                .start();

        ManagedChannel channel = InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build();

        userClient = new UserClient(channel);
    }

    @Test
    void givenUserIsPresent_whenGetUserIsCalled_ThenReturnUser() {
        User user = userClient.getUser(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("user1", user.getName());
        assertEquals("user1@example.com", user.getEmail());
    }
}

package com.baeldung.grpc.user.client;

import com.baeldung.grpc.user.User;
import com.baeldung.grpc.user.UserRequest;
import com.baeldung.grpc.user.UserResponse;
import com.baeldung.grpc.user.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserClientMockUnitTest {

    private UserClient userClient;

    @Mock
    private ManagedChannel channel;

    @Mock
    private UserServiceGrpc.UserServiceBlockingStub stub;

    @BeforeEach
    public void setup() {
        userClient = new UserClient(channel, stub);
    }

    @Test
    void givenUserIsPresent_whenGetUserIsCalled_ThenReturnUser() {
        User requiredUser = User.newBuilder()
                .setId(1)
                .setName("user1")
                .setEmail("user1@example.com")
                .build();

        Mockito.when(stub.getUser(UserRequest.newBuilder().setId(1).build()))
                .thenReturn(UserResponse.newBuilder().setUser(requiredUser).build());

        User user = userClient.getUser(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("user1", user.getName());
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    void givenUserIsNotPresent_whenGetUserIsCalled_ThenThrowRuntimeException(){
        Status expectedStatus = Status.NOT_FOUND
          .withDescription("User not found with ID 1000");
        StatusRuntimeException expectedRuntimeException = new StatusRuntimeException(expectedStatus);

        Mockito.when(stub.getUser(UserRequest.newBuilder().setId(1000).build()))
          .thenThrow(expectedRuntimeException);

        StatusRuntimeException statusRuntimeException = assertThrows(StatusRuntimeException.class,
          () -> userClient.getUser(1000));

        assertNotNull(statusRuntimeException);
        assertNotNull(statusRuntimeException.getStatus());
        assertNotNull(statusRuntimeException.getStatus().getDescription());
        assertEquals(expectedStatus.getCode(), statusRuntimeException.getStatus().getCode());
        assertEquals(expectedStatus.getDescription(), statusRuntimeException.getStatus().getDescription());
    }
}

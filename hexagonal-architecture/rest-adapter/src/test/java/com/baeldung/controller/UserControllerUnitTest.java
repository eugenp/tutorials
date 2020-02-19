package com.baeldung.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.domain.entity.user.User;
import com.baeldung.domain.exceptions.UserNotFoundException;
import com.baeldung.port.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserController userController;

    @Test
    void whenUserCreated_ThenSuccess() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Jon Doe")
                .email("jon.doe@email.com")
                .build();

        when(userServiceMock.createUser(user)).thenReturn(user);
        User userMock = userController.createUser(user);

        assertEquals(userMock, user);
    }

    @Test
    void givenUserExists_WhenUserDeleted_ThenSuccess() throws UserNotFoundException {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Jon Doe")
                .email("jon.doe@email.com")
                .build();

        when(userServiceMock.deleteUser(user.getId())).thenReturn(true);
        boolean status = userController.deleteUser(user.getId());

        assertTrue(status);
    }
}

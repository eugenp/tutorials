package com.baeldung.hexagonalarchitecture.service;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.repository.UserRepository;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserService;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserServiceImpl;
import com.baeldung.hexagonalarchitecture.serverside.mariadb.UserEntity;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldCreateUser_thenSaveIt() {
        UUID userId = userService.createUser(new UserCreateRequest("user1", 10));
        verify(userRepository).save(any(User.class));
        assertNotNull(userId);
    }

    @Test
    void shouldActiveUser_thenSaveIt() {
        User user = spy(new User(UUID.randomUUID(), "TestUser", 10, false));
        when(userRepository.findUser(user.getId())).thenReturn(user);
        userService.activeUser(user.getId());
        verify(userRepository).save(any(User.class));
        verify(user).active();
    }
}

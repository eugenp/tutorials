package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.repository.UserRepository;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserService;
import com.baeldung.hexagonalarchitecture.businesslogic.service.UserServiceImpl;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
}

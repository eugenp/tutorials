package com.baeldung.hexagonal.domain.service.impl;

import com.baeldung.hexagonal.domain.entities.User;
import com.baeldung.hexagonal.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private static UserServiceImpl userService;

    @BeforeAll
    static void init() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findById("1")).thenReturn(getDummyUser());
        userService = new UserServiceImpl(userRepository);
    }

    private static Optional<User> getDummyUser() {
        User user = new User();
        user.setId("1");
        user.setName("John");
        user.setEmail("john@gmail.com");
        user.setPassword("123456");
        user.setAddress("10, ABC Street");
        user.setCity("London");
        return Optional.of(user);
    }

    @Test
    void givenUserId_whenGetUser_thenReturnUser() {
        User user = userService.getUser("1").orElse(null);
        assertNotNull(user);
        assertEquals("John", user.getName());
    }

    @Test
    void givenUserId_whenGetUser_thenReturnEmptyOptional() {
        Optional<User> user = userService.getUser("2");
        assertFalse(user.isPresent());
    }

    @Test
    void givenUserId_whenGetEmail_thenReturnEmail() {
        String email = userService.getEmail("1");
        assertEquals("john@gmail.com", email);
    }

    @Test
    void givenUserId_whenGetEmail_thenReturnEmptyString() {
        String email = userService.getEmail("2");
        assertEquals("", email);
    }
}
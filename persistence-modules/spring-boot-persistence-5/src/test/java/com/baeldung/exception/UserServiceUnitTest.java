package com.baeldung.exception;

import com.baeldung.exception.entity.User;
import com.baeldung.exception.repository.UserRepository;
import com.baeldung.exception.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceUnitTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    @Test
    void givenExistingUsername_whenFindUserByUsernameOrThrow_thenReturnUser() {
        User user = new User("john", "john@example.com");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));
        User result = userService.findUserByUsernameOrThrow("john");
        assertEquals("john", result.getUsername());
    }

    @Test
    void givenUnknownUsername_whenFindUserByUsernameOrThrow_thenThrowUserNotFoundException() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
          () -> userService.findUserByUsernameOrThrow("ghost"));
    }

    @Test
    void givenExistingEmail_whenFindUserByEmailOrThrow_thenReturnUser() {
        User user = new User("anna", "anna@example.com");
        when(userRepository.findByEmail("anna@example.com")).thenReturn(Optional.of(user));
        User result = userService.findUserByEmailOrThrow("anna@example.com");
        assertEquals("anna@example.com", result.getEmail());
    }

    @Test
    void givenUnknownEmail_whenFindUserByEmailOrThrow_thenThrowUserNotFoundException() {
        when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
          () -> userService.findUserByEmailOrThrow("missing@example.com"));
    }
}
package com.baeldung.webflux.exceptionhandeling;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.baeldung.webflux.exceptionhandeling.controller.UserController;
import com.baeldung.webflux.exceptionhandeling.ex.NotFoundException;
import com.baeldung.webflux.exceptionhandeling.repository.UserRepository;

public class UserControllerUnitTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    public UserControllerUnitTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUserByIdUsingMonoError_UserNotFound() {
        String userId = "3";
        when(userRepository.findById(userId)).thenReturn(Mono.empty());
        StepVerifier.create(userController.getUserByIdUsingMonoError(userId))
          .expectError(NotFoundException.class)
          .verify();
    }

    @Test
    public void testGetUserByIdThrowingException_UserNotFound() {
        String userId = "3";
        when(userRepository.findById(userId)).thenReturn(Mono.empty());
        assertThrows(NotFoundException.class, () -> userController.getUserByIdThrowingException(userId)
          .block());
    }

}


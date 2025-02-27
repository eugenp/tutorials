package com.baeldung.webflux.exceptionhandeling;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.baeldung.webflux.exceptionhandeling.ex.NotFoundException;
import com.baeldung.webflux.exceptionhandeling.model.User;
import com.baeldung.webflux.exceptionhandeling.service.UserService;
import com.baeldung.webflux.exceptionhandeling.repository.UserRepository;

public class UserControllerUnitTest {
    UserRepository repositoryMock = mock(UserRepository.class);
    private final UserService userService = new UserService(repositoryMock);

    @Test
    public void givenNonExistUser_whenFailureCall_then_Throws_exception() {
        assertThrows(NotFoundException.class, () -> userService.getUserByIdThrowingException("3"));

    }

    @Test
    public void givenNonExistUser_whenFailureCall_then_returnMonoError() {
        Mono<User> result = userService.getUserByIdUsingMonoError("3");
        StepVerifier.create(result)
          .expectError(NotFoundException.class)
          .verify();

    }

}

package org.baeldung.hexagonal.controller;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.service.RegistrationService;
import org.baeldung.hexagonal.infrastructure.controller.RegistrationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationControllerUnitTest {

    private RegistrationController registrationController;

    @Mock
    RegistrationService registrationService;


    @Before
    public void setup() {
        registrationController = new RegistrationController(registrationService);

    }

    @Test
    public void validUserRegistrationIsProcessed() {
        // given
        User user = new User(LocalDateTime.now(), "aValidUserName", "aValidPassword");

        // when
        registrationController.registerUser(user);

        // then
        verify(registrationService).registerUser(any(User.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidUserRegistrationThrowsException() {
        // given
        User user = new User(LocalDateTime.now(), null, "aValidPassword");
        doThrow(new IllegalArgumentException()).when(registrationService).registerUser(any(User.class));

        // when
        registrationController.registerUser(user);
    }
}

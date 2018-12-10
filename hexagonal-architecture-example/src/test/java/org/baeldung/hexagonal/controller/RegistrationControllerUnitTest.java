package org.baeldung.hexagonal.controller;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.service.RegistrationService;
import org.baeldung.hexagonal.infrastructure.controller.RegistrationController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

public class RegistrationControllerUnitTest {

    private RegistrationController registrationController;

    @Mock
    RegistrationService registrationService;


    @Before
    public void setup() {
        registrationController = new RegistrationController(registrationService);

    }

    //TODO Verify standards for test method names
    @Test
    public void validUserRegistrationIsProcessed() {
        // given
        User user = new User(LocalDateTime.now(), "aValidUserName", "aValidPassword");

        // when
        registrationController.registerUser(user);

        // then
        //TODO validate registrationService is called
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidUserRegistrationThrowsException() {
        // given
        User user = new User(LocalDateTime.now(), null, "aValidPassword");

        // when
        registrationController.registerUser(user);
    }
}

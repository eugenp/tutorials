package org.baeldung.hexagonal.service;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.event.EventPublisher;
import org.baeldung.hexagonal.domain.repository.UserRepository;
import org.baeldung.hexagonal.domain.service.RegistrationService;
import org.baeldung.hexagonal.domain.service.RegistrationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

//TODO finish this test
public class RegistrationServiceUnitTest {

    private RegistrationService registrationService;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setup() {
        this.registrationService = new RegistrationServiceImpl(userRepository, eventPublisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUserNameIsNullThrowException() {
        // given
        User invalidUser = new User(LocalDateTime.now(), null, "somePassword");

        // when
        registrationService.registerUser(invalidUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUserNameIsEmptyThrowException() {
        // given
        User invalidUser = new User(LocalDateTime.now(), null, "somePassword");

        // when
        registrationService.registerUser(invalidUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordIsNullThrowException() {
        // given
        User invalidUser = new User(LocalDateTime.now(), "someValidUserName", null);

        // when
        registrationService.registerUser(invalidUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPasswordIsEmptyThrowException() {
        // given
        User invalidUser = new User(LocalDateTime.now(), "someValidUserName", "");

        // when
        registrationService.registerUser(invalidUser);
    }

    @Test
    public void whenValidUserProcessSuccessfully() {
        // given
        User validUser = new User(LocalDateTime.now(), "someValidUserName", "someValidPassword");

        // when
        registrationService.registerUser(validUser);

        // then
        //TODO validate calls to eventPublisher and userRepository
    }




}

package com.baeldung.hexagonalexample.registration;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RegistrationServiceUnitTest {

    @Test
    public void someTest() {

        // given
        RegistrationPersistencePort mockedPersistencePort = new RegistrationPersistencePort() {

            private List<Registration> registrations = new ArrayList<>();

            @Override
            public List<Registration> findAll() {
                return registrations;
            }

            @Override
            public Registration save(Registration registration) {
                registrations.add(registration);
                return registration;
            }
        };

        RegistrationService registrationService = new RegistrationService(mockedPersistencePort);

        // when
        String emailAddress = "some@user.org";
        registrationService.register(emailAddress);

        // then
        assertEquals(1, registrationService.fetchAllRegistrations()
            .size());
        assertEquals(emailAddress, registrationService.fetchAllRegistrations()
            .get(0)
            .getEmailAddress());
    }
}

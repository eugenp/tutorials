package com.baeldung.hexagonalexample.registration;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RegistrationServiceUnitTest {

    @Test
    public void someTest() {

        List<Registration> registrationList = new ArrayList<>();

        // given
        RegistrationPersistencePort mockedPersistencePort = new RegistrationPersistencePort() {

            private List<Registration> registrations = registrationList;

            @Override
            public Registration save(Registration registration) {
                registrations.add(registration);
                return registration;
            }
        };

        RegistrationService registrationService = new RegistrationService().setRegistrationPersistencePort(mockedPersistencePort);

        // when
        String emailAddress = "some@user.org";
        registrationService.register(emailAddress);

        // then
        assertEquals(1, registrationList.size());
        assertEquals(emailAddress, registrationList.get(0)
            .getEmailAddress());
    }
}

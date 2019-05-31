package com.baeldung.hexagonalexample.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RegistrationService implements RegistrationUserInterfacePort {

    @Autowired
    private RegistrationPersistencePort registrationPersistencePort;

    @Override
    public Registration register(String emailAddress) {

        Registration registration = new Registration(emailAddress);
        return registrationPersistencePort.save(registration);
    }

    RegistrationService setRegistrationPersistencePort(RegistrationPersistencePort registrationPersistencePort) {
        this.registrationPersistencePort = registrationPersistencePort;
        return this;
    }
}

package com.baeldung.hexagonalexample.registration;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RegistrationService implements RegistrationUserInterfacePort {

    private RegistrationPersistencePort registrationPersistencePort;

    public RegistrationService(RegistrationPersistencePort registrationPersistencePort) {
        this.registrationPersistencePort = registrationPersistencePort;
    }

    @Override
    public List<Registration> fetchAllRegistrations() {
        return registrationPersistencePort.findAll();
    }

    @Override
    public Registration register(String emailAddress) {
        Registration registration = new Registration(emailAddress);
        return registrationPersistencePort.save(registration);
    }
}

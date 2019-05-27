package com.baeldung.hexagonalexample.registration;

import java.util.List;

interface RegistrationPersistencePort {

    List<Registration> findAll();

    Registration save(Registration registration);
}

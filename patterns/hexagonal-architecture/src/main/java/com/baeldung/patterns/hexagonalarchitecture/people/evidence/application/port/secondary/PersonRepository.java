package com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.secondary;

import java.util.Optional;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;

public interface PersonRepository {

    Optional<Person> findByName(String personName);
}

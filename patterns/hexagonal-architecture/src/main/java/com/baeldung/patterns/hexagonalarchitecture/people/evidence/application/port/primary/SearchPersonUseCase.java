package com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;

import java.util.Optional;

public interface SearchPersonUseCase {

    Optional<Person> findByName(String personName);
}

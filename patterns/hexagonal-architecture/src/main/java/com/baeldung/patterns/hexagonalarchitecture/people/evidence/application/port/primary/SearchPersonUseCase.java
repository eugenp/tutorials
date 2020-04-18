package com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary;

import java.util.Optional;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.secondary.PersonRepository;

public class SearchPersonUseCase {

    private PersonRepository personRepository;

    public SearchPersonUseCase(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findByName(String personName) {
        return personRepository.findByName(personName);
    }
}

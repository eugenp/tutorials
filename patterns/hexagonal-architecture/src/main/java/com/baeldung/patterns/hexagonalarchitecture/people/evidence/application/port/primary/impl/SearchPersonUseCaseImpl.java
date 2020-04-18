package com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary.impl;

import java.util.Optional;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary.SearchPersonUseCase;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.secondary.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchPersonUseCaseImpl implements SearchPersonUseCase {

    private PersonRepository personRepository;

    public SearchPersonUseCaseImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findByName(String personName) {
        return personRepository.findByName(personName);
    }
}

package com.baeldung.patterns.hexagonalarchitecture.people.evidence.adapter.persistence;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.secondary.PersonRepository;

@Repository
public class InMemoryPersonRepository implements PersonRepository {

    private List<Person> people = Arrays.asList(
            new Person("Peter", 20),
            new Person("Anna", 22),
            new Person("Jack", 30));

    @Override
    public Optional<Person> findByName(String personName) {
        return people.stream()
            .filter(person -> person.getName().equals(personName))
            .findFirst();
    }
}

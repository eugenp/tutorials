package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.domain.dto.CreatePersonDto;

import java.util.UUID;

public class PersonService implements PersonPort {

    private final PersonRepositoryPort personRepositoryPort;

    public PersonService(PersonRepositoryPort personRepositoryPort) {
        this.personRepositoryPort = personRepositoryPort;
    }

    @Override
    public void save(UUID id, CreatePersonDto request) {
        personRepositoryPort.save(new Person(id, request.getName()));
    }

    @Override
    public Person getById(UUID id) {
        return personRepositoryPort
          .findById(id)
          .orElseThrow((() -> new RuntimeException("Could not find Todo with id = " + id)));
    }
}

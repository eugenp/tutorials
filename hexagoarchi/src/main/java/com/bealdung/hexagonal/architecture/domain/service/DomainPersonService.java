package com.bealdung.hexagonal.architecture.domain.service;

import java.util.List;
import java.util.Optional;

import com.bealdung.hexagonal.architecture.domain.model.Person;
import com.bealdung.hexagonal.architecture.domain.repository.PersonRepositoryPort;

public class DomainPersonService implements PersonServicePort {

    private PersonRepositoryPort personRepositoryPort;

    public DomainPersonService(PersonRepositoryPort personRepositoryPort) {
        this.personRepositoryPort = personRepositoryPort;
    }

    public List<Person> findAll() {
        return this.personRepositoryPort.findAll();
    }

    public Optional<Person> findById(int id) {
        return this.personRepositoryPort.findById(id);
    }

    public Person save(Person person) {
        return this.personRepositoryPort.save(person);
    }

    public void delete(int id) {
        this.personRepositoryPort.delete(id);
    }

}

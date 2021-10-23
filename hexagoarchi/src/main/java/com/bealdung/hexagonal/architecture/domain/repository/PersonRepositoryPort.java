package com.bealdung.hexagonal.architecture.domain.repository;

import java.util.List;
import java.util.Optional;

import com.bealdung.hexagonal.architecture.domain.model.Person;

public interface PersonRepositoryPort {

    List<Person> findAll();

    Optional<Person> findById(int id);

    Person save(Person person);

    void delete(int id);
}

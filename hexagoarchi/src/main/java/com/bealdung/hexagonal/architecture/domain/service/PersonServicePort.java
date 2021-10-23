package com.bealdung.hexagonal.architecture.domain.service;

import java.util.List;
import java.util.Optional;

import com.bealdung.hexagonal.architecture.domain.model.Person;

public interface PersonServicePort {

    List<Person> findAll();

    Optional<Person> findById(int id);

    Person save(Person person);

    void delete(int id);

}

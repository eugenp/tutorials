package com.baeldung.hexagonal.persistence;

import com.baeldung.hexagonal.domain.Person;

/**
 * Abstraction on storing a set of {@link Person} instances.
 */
public interface PersonRepository {

    Person getById(int id);

    void save(Person person);
}

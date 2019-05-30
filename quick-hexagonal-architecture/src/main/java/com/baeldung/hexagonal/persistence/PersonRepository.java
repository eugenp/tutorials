package com.baeldung.hexagonal.persistence;

import com.baeldung.hexagonal.domain.Person;

public interface PersonRepository {

    Person getById(int id);

    void save(Person person);
}

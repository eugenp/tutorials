package com.baeldung.jpa.projection.repository;

import org.springframework.data.repository.Repository;

import com.baeldung.jpa.projection.model.Person;
import com.baeldung.jpa.projection.view.PersonDto;
import com.baeldung.jpa.projection.view.PersonView;

public interface PersonRepository extends Repository<Person, Long> {
    PersonView findByLastName(String lastName);

    PersonDto findByFirstName(String firstName);

    <T> T findByLastName(String lastName, Class<T> type);
}

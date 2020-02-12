package com.baeldung.projection.repository;

import com.baeldung.projection.model.Person;
import com.baeldung.projection.view.PersonDto;
import com.baeldung.projection.view.PersonView;
import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, Long> {
    PersonView findByLastName(String lastName);

    PersonDto findByFirstName(String firstName);

    <T> T findByLastName(String lastName, Class<T> type);
}

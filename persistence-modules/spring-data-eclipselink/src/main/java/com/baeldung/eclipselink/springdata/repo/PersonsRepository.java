package com.baeldung.eclipselink.springdata.repo;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.eclipselink.springdata.model.Person;

/**
 * Created by adam.
 */
public interface PersonsRepository extends CrudRepository<Person, Long> {

    Person findByFirstName(String firstName);

}

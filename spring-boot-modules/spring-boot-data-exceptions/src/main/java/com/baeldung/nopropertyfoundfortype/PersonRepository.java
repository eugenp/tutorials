package com.baeldung.nopropertyfoundfortype;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByFirstName(String firstName);

    List<Person> findByLastName(String lastName);

    // Below same function will generate the error
    // List<Person> findByLastname(String lastName);

}

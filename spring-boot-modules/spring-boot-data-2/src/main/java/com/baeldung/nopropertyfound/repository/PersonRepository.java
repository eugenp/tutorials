package com.baeldung.nopropertyfound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.nopropertyfound.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    // findByFirsttName will cause Spring Data to throw PropertyReferenceException
    // Person findByFirsttName(String firstName);
    Person findByFirstName(String firstName);

}

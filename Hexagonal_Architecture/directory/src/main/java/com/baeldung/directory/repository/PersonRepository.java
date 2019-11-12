package com.baeldung.directory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.directory.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByName(final String name);

}

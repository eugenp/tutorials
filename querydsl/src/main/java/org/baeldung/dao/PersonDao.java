package org.baeldung.dao;

import org.baeldung.entity.Person;

import java.util.List;

public interface PersonDao {

    public Person save(Person person);

    public List<Person> findPersonsByFirstnameQueryDSL(String firstname);
}
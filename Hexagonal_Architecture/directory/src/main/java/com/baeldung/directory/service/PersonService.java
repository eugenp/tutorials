package com.baeldung.directory.service;

import java.util.List;

import com.baeldung.directory.model.Person;

public interface PersonService {

    List<Person> getAllPersons();

    Person getPersonWithName(final String name);

    String addPerson(final Person person);

}

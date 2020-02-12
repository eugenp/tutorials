package org.baeldung.resttemplate.web.service;

import org.baeldung.resttemplate.web.dto.Person;

public interface PersonService {

    public Person saveUpdatePerson(Person person);

    public Person findPersonById(Integer id);
}
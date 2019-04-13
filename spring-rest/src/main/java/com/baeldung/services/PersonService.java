package com.baeldung.services;

import org.baeldung.web.dto.Person;

public interface PersonService {

	public Person saveUpdatePerson(Person person);

	public Person findPersonById(Integer id);
}

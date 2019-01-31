package com.baeldung.springboot.service;

import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.exception.PersonException;
import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.model.dto.PersonDto;

public interface PersonService {

	public PersonResponse createPerson(PersonDto personDto);
	
	public Person getPersonById(Long personId) throws PersonException;

}

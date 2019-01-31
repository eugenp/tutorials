package com.baeldung.springboot.service;

import java.util.Optional;

import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.model.dto.PersonDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.springboot.exception.PersonException;
import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.respository.PersonRepository;


@Service
public class PersonServiceImpl implements PersonService {

	private final PersonRepository personRepository;

	private final ModelMapper modelMapper;

	@Autowired
	public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper) {

		this.personRepository = personRepository;

		this.modelMapper = modelMapper;
	}

	@Override
	public PersonResponse createPerson(PersonDto personDto) {

		Person personEntity = modelMapper.map(personDto, Person.class);

		Person person =  personRepository.save(personEntity);

		return new PersonResponse("Registration Success.", String.valueOf(person.getId()));
	}

	@Override
	public Person getPersonById(Long personId) throws PersonException {

		Optional<Person> person = personRepository.findById(personId);

		return person.orElseThrow(() -> new PersonException(personId.toString(), "404", "Person Not Found"));

	}

}

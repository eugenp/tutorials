package com.baeldung.architecture_hexagonal.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.architecture_hexagonal.port.PersonRepositoryPort;

@Service
public class PersonService {

	@Autowired
	private PersonRepositoryPort personRepository;

	public void create(String name, String surname, String phoneNumber) {
		personRepository.create(name, surname, phoneNumber);
	}

	public Person view(Long id) {
		return personRepository.getPerson(id);
	}

	public List<Person> viewPhonebook() {
		return personRepository.getPhonebook();
	}

}

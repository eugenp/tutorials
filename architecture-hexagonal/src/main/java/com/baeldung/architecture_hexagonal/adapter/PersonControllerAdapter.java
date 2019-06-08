package com.baeldung.architecture_hexagonal.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture_hexagonal.core.Person;
import com.baeldung.architecture_hexagonal.core.PersonService;
import com.baeldung.architecture_hexagonal.port.PersonUIPort;

@RestController
@RequestMapping("/persons/")
public class PersonControllerAdapter implements PersonUIPort {

	@Autowired
	private PersonService personService;

	@Override
	public void create(@RequestBody Person request) {
		personService.create(request.getName(), request.getSurname(), request.getPhoneNumber());
	}

	@Override
	public Person view(@PathVariable Long id) {
		Person person = personService.view(id);
		return person;
	}

	@Override
	public List<Person> getPhonebook() {
		return personService.viewPhonebook();
	}

}

package com.baeldung.springboot.controller;

import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.exception.PersonException;
import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.model.dto.PersonDto;
import com.baeldung.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

	private final PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		
		this.personService = personService;

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> getperson(@PathVariable Long id) throws PersonException {
		
		return new ResponseEntity<>(personService.getPersonById(id), HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<PersonResponse> registerRunner(@RequestBody PersonDto personDto) {
		
		return new ResponseEntity<>(personService.createPerson(personDto), HttpStatus.CREATED);

	}
	
}

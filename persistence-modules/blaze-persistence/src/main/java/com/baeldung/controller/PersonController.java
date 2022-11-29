package com.baeldung.controller;

import com.baeldung.model.Person;
import com.baeldung.repository.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @GetMapping("/person")
    Iterable<Person> findPerson() {
        return personRepository.find();
    }
}

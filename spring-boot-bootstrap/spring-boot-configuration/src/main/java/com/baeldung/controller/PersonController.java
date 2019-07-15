package com.baeldung.controller;

import com.baeldung.domain.Person;
import com.baeldung.exception.PersonNotFoundException;
import com.baeldung.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @PostMapping
    public void addPerson(@RequestBody Person person) {
        personService.add(person);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable(required = true) long id) throws PersonNotFoundException {
        return personService.getPersonById(id);
    }

    @DeleteMapping("/{id}")
    public void removePerson(@PathVariable(required = true) long id) {
        personService.delete(id);
    }


}

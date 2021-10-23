package com.bealdung.hexagonal.architecture.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bealdung.hexagonal.architecture.api.exception.PersonNotFoundException;
import com.bealdung.hexagonal.architecture.domain.model.Person;
import com.bealdung.hexagonal.architecture.domain.service.PersonServicePort;

@RestController
public class PersonRestController {

    private final PersonServicePort personService;

    public PersonRestController(PersonServicePort personService) {
        this.personService = personService;
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
        return personService.findAll();
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Integer id) {
        Person person = personService.findById(id)
            .orElseThrow(() -> new PersonNotFoundException("Person with ID :" + id + " Not Found!"));

        return ResponseEntity.ok()
            .body(person);
    }

    @PostMapping("/persons")
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        Person std = personService.save(person);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(std.getId())
            .toUri();

        return ResponseEntity.created(location)
            .build();
    }

    @DeleteMapping("/persons/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Integer id) {
        Person person = personService.findById(id)
            .orElseThrow(() -> new PersonNotFoundException("Person with ID :" + id + " Not Found!"));

        personService.delete(person.getId());
        return ResponseEntity.ok()
            .body("Person deleted with success!");
    }

}
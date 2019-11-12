package com.baeldung.directory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.directory.model.Person;
import com.baeldung.directory.service.PersonService;

@RestController
public class DirectoryControlller {

    @Autowired
    private PersonService service;

    @GetMapping("/getAllPersons")
    public List<Person> getAllPersons() {
        return service.getAllPersons();
    }

    @GetMapping("/getPerson/{name}")
    public Person getPerson(@PathVariable("name") final String name) {
        return service.getPersonWithName(name);
    }

    @PostMapping("/addPerson")
    public ResponseEntity<?> addPerson(@RequestBody final Person person) {
        String result = service.addPerson(person);
        if (result.equalsIgnoreCase("Success")) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("An error occurred. Please contact support.");
    }

}

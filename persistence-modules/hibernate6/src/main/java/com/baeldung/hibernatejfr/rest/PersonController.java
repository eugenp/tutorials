package com.baeldung.hibernatejfr.rest;

import com.baeldung.hibernatejfr.entity.PersonEntity;
import com.baeldung.hibernatejfr.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jfr")
public class PersonController {

    @Autowired
    PersonService personService;

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/persons/{id}")
    public PersonEntity getPerson(@PathVariable("id") Long id) {
        return personService.findPersonById(id);
    }

    @GetMapping("/persons")
    public List<PersonEntity> listPerson() {
        return personService.listPersons();
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<PersonEntity> updatePerson(@PathVariable("id") Long id, @RequestBody PersonEntity person){
        try {
            PersonEntity entity = personService.updatePerson(id, person);
            return new ResponseEntity<>(entity, HttpStatus.OK);
        }
        catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
    }

    @PostMapping("/persons/batch")
    public void batchCreatePerson(@RequestBody List<PersonEntity> persons) {
        personService.createPersons(persons);
    }
}
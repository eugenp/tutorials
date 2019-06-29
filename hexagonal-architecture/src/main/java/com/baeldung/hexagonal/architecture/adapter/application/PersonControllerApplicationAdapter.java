package com.baeldung.hexagonal.architecture.adapter.application;

import com.baeldung.hexagonal.architecture.domain.Person;
import com.baeldung.hexagonal.architecture.port.application.PersonApplicationPort;
import com.baeldung.hexagonal.architecture.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonControllerApplicationAdapter implements PersonApplicationPort {

    @Autowired
    private PersonService personService;

    @Override
    public void addPerson(Person person) {
        personService.addPerson(person);
    }

    @Override
    public Person getPerson(int personId) {
        return personService.getPerson(personId);
    }
}

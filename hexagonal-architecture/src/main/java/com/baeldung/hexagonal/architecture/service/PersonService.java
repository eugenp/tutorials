package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.port.infrastructure.PersonInfrastructurePort;
import com.baeldung.hexagonal.architecture.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonInfrastructurePort personInfrastructurePort;

    public void addPerson(Person person) {
        personInfrastructurePort.addPerson(person);
    }

    public Person getPerson(int personId) {
        Person person = personInfrastructurePort.getPerson(personId);
        return person;
    }
}

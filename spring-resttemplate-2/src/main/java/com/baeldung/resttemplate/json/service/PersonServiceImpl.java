package com.baeldung.resttemplate.json.service;

import com.baeldung.resttemplate.json.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonServiceImpl implements PersonService {

    @Override
    public Person saveUpdatePerson(Person person) {
        return person;
    }

    @Override
    public Person findPersonById(Integer id) {
        return new Person(id, "John");
    }

}

package com.baeldung.resttemplate.web.service;

import com.baeldung.resttemplate.web.dto.Person;
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

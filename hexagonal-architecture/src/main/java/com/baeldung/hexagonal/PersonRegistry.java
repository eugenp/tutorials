package com.baeldung.hexagonal;

import javax.inject.Inject;

public class PersonRegistry {

    @Inject
    PersonService service;

    public void createPerson(Person input) {
        Person data = new Person();
        // fill person with input data
        service.persist(data);
    }

}

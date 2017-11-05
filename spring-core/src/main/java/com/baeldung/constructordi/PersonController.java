package com.baeldung.constructordi;

import org.springframework.beans.factory.annotation.Autowired;

public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public PersonService getPersonService() {
        return this.personService;
    }

}

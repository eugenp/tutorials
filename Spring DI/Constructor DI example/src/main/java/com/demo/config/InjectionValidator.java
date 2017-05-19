package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.example.Person;

@Component
public class InjectionValidator {

    private Person person;

    @Autowired
    public InjectionValidator(Person person) {
        super();
        this.person = person;
    }

    public InjectionValidator() {
        super();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}

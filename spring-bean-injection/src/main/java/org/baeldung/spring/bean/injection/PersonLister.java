package org.baeldung.spring.bean.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonLister {

    private Person person;

    @Autowired
    public PersonLister(Person person) {
        this.person = person;
    }

    public String printName() {
        return this.person.getName();
    }
}

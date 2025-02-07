package com.baeldung.multibeaninstantiation.solution2;

import org.springframework.stereotype.Component;

@Component
public class PersonTwo extends Person {

    public PersonTwo() {
        super("John", "Reese");
    }
}
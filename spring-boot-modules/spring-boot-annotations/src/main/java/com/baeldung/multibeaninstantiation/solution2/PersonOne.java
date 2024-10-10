package com.baeldung.multibeaninstantiation.solution2;

import org.springframework.stereotype.Component;

@Component
public class PersonOne extends Person {

    public PersonOne() {
        super("Harold", "Finch");
    }
}
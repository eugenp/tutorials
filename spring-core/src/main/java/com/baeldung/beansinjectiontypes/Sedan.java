package com.baeldung.beansinjectiontypes;

import org.springframework.stereotype.Component;

@Component
public class Sedan implements Car {
    public void honk(){
        System.out.println("I'm a sedan!");
    }
}

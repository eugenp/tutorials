package com.baeldung.core.modifiers.otherpackage;

import com.baeldung.core.modifiers.Person;

public class Thief {
    Person person;
    
    public Thief(String myName, int age) {
        
        // The constructor Person(String, String, int) is not visible

        //person = new Person("Mr", myName, age);
        person = new Person();
    }
}

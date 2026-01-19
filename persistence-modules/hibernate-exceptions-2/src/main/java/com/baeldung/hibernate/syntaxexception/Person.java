package com.baeldung.hibernate.syntaxexception;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    
    public Person() {
    }
    
    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
}

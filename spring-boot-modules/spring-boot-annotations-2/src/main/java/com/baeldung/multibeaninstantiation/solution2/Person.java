package com.baeldung.multibeaninstantiation.solution2;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String firstName, String secondName) {
        super();
        this.firstName = firstName;
        this.lastName = secondName;
    }

    @Override
    public String toString() {
        return "Person [firstName=" + firstName + ", secondName=" + lastName + "]";
    }
}
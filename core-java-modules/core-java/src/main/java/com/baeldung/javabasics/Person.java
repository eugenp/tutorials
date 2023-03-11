package com.baeldung.javabasics;

import lombok.Data;

@Data
public class Person implements Cloneable {
    private String name;
    private Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    //Copy constructor
    public Person (Person otherPerson) {
        this.name = otherPerson.name;
        this.address = new Address(otherPerson.getAddress().getStreet(), otherPerson.getAddress().getCity());
    }

    //Copy factory method
    public static Person copyOf(Person otherPerson) {
        return new Person(otherPerson.name, new Address(otherPerson.getAddress().getStreet(),
                otherPerson.getAddress().getCity()));
    }
}

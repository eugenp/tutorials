package com.baeldung.pojo;

public class PersonDeepCopy {
    String name;
    Address address;

    PersonDeepCopy(String name, Address address) {
        this.name = name;
        this.address = address;
    }
    // Copy Constructor for Deep Copy.
    PersonDeepCopy(PersonDeepCopy person) {
        this.name = person.name;
        this.address = new Address(person.address);
    }

    protected Object clone() throws CloneNotSupportedException {
        return new PersonDeepCopy(this);    // Deep Copy
    }
}

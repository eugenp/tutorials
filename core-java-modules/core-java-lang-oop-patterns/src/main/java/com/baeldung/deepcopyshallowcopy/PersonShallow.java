package com.baeldung.deepcopyshallowcopy;

import java.lang.Cloneable;

public class PersonShallow {
    String name;
    int age;
    Address address;

    public PersonShallow(String name, int age, Address address) {
        this.name = name;
        this.age = age;
    }

    public PersonShallow(PersonShallow another) {
        this.name = another.name;
        this.age = another.age;
        this.address = another.address;
    }
}
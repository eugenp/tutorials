package com.baeldung.deepcopyshallowcopy;

import java.lang.Cloneable;

public class PersonShallow {
    String name;
    int age;
    Address address;

    public PersonShallow(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
}
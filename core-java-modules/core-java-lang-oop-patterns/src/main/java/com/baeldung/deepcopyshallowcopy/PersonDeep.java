package com.baeldung.deepcopyshallowcopy;

import java.lang.Cloneable;

public class PersonDeep implements Cloneable {
    String name;
    int age;
    Address address;

    public PersonDeep(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public PersonDeep clone() {
        PersonDeep cloned = null;
        try {
            cloned = (PersonDeep) super.clone();
            cloned.address = (Address) this.address.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        return cloned;
    }
}
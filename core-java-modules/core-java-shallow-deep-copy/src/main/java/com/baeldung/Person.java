package com.baeldung;


import java.io.Serializable;

public class Person implements Cloneable, Serializable {
    private String name;
    private Address address;

    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
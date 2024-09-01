package com.baeldung.deepshallowcopyv2;

public class Person implements Cloneable {
    private String name;
    private Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Shallow copy for Person
    }

    public Person deepClone() throws CloneNotSupportedException {
        Person clonedPerson = (Person) super.clone(); // Shallow copy of Person
        clonedPerson.address = (Address) address.clone(); // Deep copy of Address
        return clonedPerson;
    }
}
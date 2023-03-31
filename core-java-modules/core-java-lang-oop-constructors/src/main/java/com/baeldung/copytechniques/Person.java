package com.baeldung.copyconstructor;

public class Person {
    public int id;
    public String name;
    public Address address;

    public Person(int id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError();
        }
    }
}
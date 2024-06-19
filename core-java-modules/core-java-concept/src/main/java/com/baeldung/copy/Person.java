package com.baeldung.copy;

public class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Person deepClone() throws CloneNotSupportedException {
        Person clone = (Person) this.clone();
        clone.address = new Address(this.address.city);
        return clone;
    }
}

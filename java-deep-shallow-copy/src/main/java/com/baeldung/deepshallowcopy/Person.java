package com.baeldung.deepshallowcopy;

public class Person implements Cloneable {
    private final String name;
    private final Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }

    public Person deepCopy() {
        return new Person(this.name, new Address(this.address.getStreet()));
    }
}
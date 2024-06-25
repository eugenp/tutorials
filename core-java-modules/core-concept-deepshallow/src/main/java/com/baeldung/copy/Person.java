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
        Person clone = (Person) super.clone();
        clone.address = new Address(this.address.city); // Ensure deep clone of Address
        return clone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}

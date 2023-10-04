package core;

import core.Address;

public class Person implements Cloneable {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        // Shallow copy
        Person clonedPerson = (Person) super.clone();
        // Since Address also implements Cloneable, we can clone it as well
        clonedPerson.address = (Address) address.clone();
        return clonedPerson;
    }

    // Shallow copy implementation
    public Person shallowCopy() {
        return new Person(this.name, this.address);
    }

    // Standard Getters and Setters
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

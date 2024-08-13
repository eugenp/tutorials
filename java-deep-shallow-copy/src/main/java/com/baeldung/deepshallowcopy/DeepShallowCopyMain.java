package com.baeldung.deepshallowcopy;

import java.util.logging.Logger;

/**
 * Main codebase
 */
class Address implements Cloneable {
    private String street;

    public Address(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // shallow copy
    }

    @Override
    public String toString() {
        return street;
    }
}

class Person implements Cloneable {
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
        return super.clone(); // shallow copy
    }

    public Person deepCopy() {
        Address newAddress = new Address(this.address.getStreet());
        return new Person(this.name, newAddress);
    }

    @Override
    public String toString() {
        return name + ", " + address;
    }
}

// Immutable class example
final class ImmutablePerson {
    private final String name;
    private final Address address;

    public ImmutablePerson(String name, Address address) {
        this.name = name;
        this.address = new Address(address.getStreet());
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return new Address(address.getStreet());
    }

    @Override
    public String toString() {
        return name + ", " + address;
    }
}

public class DeepShallowCopyMain {
    private static final Logger logger = Logger.getLogger(DeepShallowCopyMain.class.getName());

    public static void main(String[] args) throws CloneNotSupportedException {
        // Shallow Copy
        logger.info("Demonstrating Shallow Copy:");
        Address address1 = new Address("123 Main St");
        Person person1 = new Person("Alice", address1);
        Person person2 = (Person) person1.clone();

        logger.info("Original person: " + person1);
        logger.info("Shallow copy person: " + person2);

        person2.getAddress().setStreet("456 Elm St");
        logger.info("After modifying the shallow copy's address:");
        logger.info("Original person: " + person1);
        logger.info("Shallow copy person: " + person2);
        logger.info("");

        // Deep Copy
        logger.info("Demonstrating Deep Copy:");
        Person person3 = person1.deepCopy();

        logger.info("Original person: " + person1);
        logger.info("Deep copy person: " + person3);

        person3.getAddress().setStreet("789 Oak St");
        logger.info("After modifying the deep copy's address:");
        logger.info("Original person: " + person1);
        logger.info("Deep copy person: " + person3);
        logger.info("");

        // Immutable Object
        logger.info("Demonstrating Immutable Object:");
        ImmutablePerson immutablePerson1 = new ImmutablePerson("Alice", address1);
        ImmutablePerson immutablePerson2 = new ImmutablePerson("Alice", new Address("456 Elm St"));

        logger.info("Immutable person 1: " + immutablePerson1);
        logger.info("Immutable person 2: " + immutablePerson2);

        logger.info("Attempting to modify the address of immutable person 1:");
        address1.setStreet("101 Maple St");
        logger.info("Immutable person 1: " + immutablePerson1);
        logger.info("Original address: " + address1);
    }
}

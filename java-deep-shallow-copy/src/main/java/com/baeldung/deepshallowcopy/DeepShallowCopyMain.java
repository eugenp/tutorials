package com.baeldung.deepshallowcopy;

import java.util.logging.Logger;

/**
 * Main codebase
 */
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

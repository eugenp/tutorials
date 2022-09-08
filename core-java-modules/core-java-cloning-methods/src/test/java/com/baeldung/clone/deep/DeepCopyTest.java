package com.baeldung.clone.deep;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class DeepCopyTest {

    @Test
    public void testDeepCopyCloneMethod_ValueNotUpdated() throws CloneNotSupportedException {
        Address address = new Address("215", "HighStr.", "10912", "Berlin", "Germany");
        Person person = new Person("John Doe", 34, address);

        Person personCopy = (Person) person.clone();
        personCopy.getAddress()
          .setHouseNumber("216");

        assertFalse(person.toString()
          .equals(personCopy.toString()));
        assertFalse(person.getAddress()
          .getHouseNumber()
          .equals(personCopy.getAddress()
            .getHouseNumber()));
        assertNotEquals(person.getAddress(), personCopy.getAddress());
    }

    @Test
    public void testDeepCopyConstructorMethod_ValueNotUpdated() throws CloneNotSupportedException {
        Address address = new Address("215", "HighStr.", "10912", "Berlin", "Germany");
        Person person = new Person("John Doe", 34, address);

        Person personCopy = new Person(person);
        personCopy.getAddress()
          .setHouseNumber("216");

        assertFalse(person.toString()
          .equals(personCopy.toString()));
        assertFalse(person.getAddress()
          .getHouseNumber()
          .equals(personCopy.getAddress()
            .getHouseNumber()));
        assertNotEquals(person.getAddress(), personCopy.getAddress());
    }

}

package com.baeldung.clone.shallow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class ShallowCopyTest {

    @Test
    public void testShallowCopyCloneMethod_ValueNotUpdated() throws CloneNotSupportedException {
        Address address = new Address("215", "HighStr.", "10912", "Berlin", "Germany");
        Person person = new Person("John Doe", 34, address);

        Person personCopy = (Person) person.clone();
        personCopy.getAddress()
          .setHouseNumber("216");

        assertTrue(person.toString()
          .equals(personCopy.toString()));
        assertTrue(person.getAddress()
          .getHouseNumber()
          .equals(personCopy.getAddress()
            .getHouseNumber()));
        assertEquals(person.getAddress(), personCopy.getAddress());
    }

    @Test
    public void testShallowCopyConstructorMethod_ValueNotUpdated() throws CloneNotSupportedException {
        Address address = new Address("215", "HighStr.", "10912", "Berlin", "Germany");
        Person person = new Person("John Doe", 34, address);

        Person personCopy = new Person(person);
        personCopy.getAddress()
          .setHouseNumber("216");

        assertTrue(person.toString()
          .equals(personCopy.toString()));
        assertTrue(person.getAddress()
          .getHouseNumber()
          .equals(personCopy.getAddress()
            .getHouseNumber()));
        assertEquals(person.getAddress(), personCopy.getAddress());
    }

}

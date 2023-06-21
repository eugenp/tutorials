package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    public void testShallowCopy() {
        Address address = new Address("Manchester");
        Person originalPerson = new Person("Henry", address);
        Person copiedPerson = originalPerson.shallowCopy();

        // Assert that the copiedPerson is a new object
        assertNotSame(originalPerson, copiedPerson);

        // Assert that the name is the same in both objects
        assertEquals(originalPerson.getName(), copiedPerson.getName());

        // Assert that the address reference is the same in both objects
        assertSame(originalPerson.getAddress(), copiedPerson.getAddress());
    }
}
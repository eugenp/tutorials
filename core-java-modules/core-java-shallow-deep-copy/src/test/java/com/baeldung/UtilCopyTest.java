package com.baeldung;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilCopyTest  {

    @Test
    public void testShallowCopy() {
        // Create original object
        Person person = new Person();
        person.setName("Joe Doe");
        Address address = new Address();
        address.setValue("123 Sesame Street");
        person.setAddress(address);

        // Create a shallow copy and modify the address
        Person copy = UtilCopy.shallowCopy(person);
        copy.getAddress().setValue("4 Privet Drive");

        // Check objects (person is different but address is same object)
        assertNotSame(person, copy);
        assertSame(person.getAddress(), copy.getAddress());

        // Name is the same
        assertEquals(copy.getName(), person.getName());

        // Addresses are the same (note that only copy was changed)
        assertEquals(copy.getAddress().getValue(), person.getAddress().getValue());

        // The original object 'person' shares the same address object
        assertEquals("4 Privet Drive", person.getAddress().getValue());
        assertEquals("4 Privet Drive", copy.getAddress().getValue());
    }

    @Test
    public void testDeepCopy() {

        // Create original object
        Person person = new Person();
        person.setName("Joe Doe");
        Address address = new Address();
        address.setValue("123 Sesame Street");
        person.setAddress(address);

        // Create a shallow copy and modify the address
        Person copy = UtilCopy.deepCopy(person);
        copy.getAddress().setValue("4 Privet Drive");

        // Check objects (both person and address are different objects)
        assertNotSame(person, copy);
        assertNotSame(person.getAddress(), copy.getAddress());

        // Name is the same
        assertEquals(copy.getName(), person.getName());

        // Addresses are different
        assertNotEquals(copy.getAddress().getValue(), person.getAddress().getValue());

        // The original object 'person' and the 'copy' use different address objects
        assertEquals("123 Sesame Street", person.getAddress().getValue());
        assertEquals("4 Privet Drive", copy.getAddress().getValue());
    }
}
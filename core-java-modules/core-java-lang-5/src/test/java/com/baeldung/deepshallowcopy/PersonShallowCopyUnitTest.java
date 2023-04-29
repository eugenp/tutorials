package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PersonShallowCopyUnitTest {

    @Test
    void whenShallowCopyObjectShouldNotBeSame() throws CloneNotSupportedException {
        Address address = new Address("Dhaka", "Bangladesh", 1840);

        // creation of original person object
        Person original = new Person(1L, "Nabil", 25, address);

        // copied or cloned the original object
        Person copied = (Person) original.clone();

        assertNotEquals(original, copied); // objects are not same
        assertEquals(original.getAddress(), copied.getAddress()); // however, their reference are same
    }

    @Test
    void whenShallowCopyUsedFieldsAreCopiedAsIs() throws CloneNotSupportedException {
        Address address = new Address("Dhaka", "Bangladesh", 1840);

        // creation of original person object
        Person original = new Person(1L, "Nabil", 25, address);

        // copied or cloned the original object
        Person copied = (Person) original.clone();

        // Check If they are equal without modification
        assertEquals(original.getUserId(), copied.getUserId());
        assertEquals(original.getUsername(), copied.getUsername());
        assertEquals(original.getAge(), copied.getAge());
    }

    @Test
    void whenShallowCopyUsedWithModifiedFieldTypesAndReferenceType() throws CloneNotSupportedException {
        Address address = new Address("Dhaka", "Bangladesh", 1840);

        // creation of original person object
        Person original = new Person(1L, "Nabil", 25, address);

        // copied or cloned the original object
        Person copied = (Person) original.clone();

        // Let's change field types the copied object
        copied.setUserId(1001L);
        copied.setUsername("Partha");
        copied.setAge(26);

        // change the reference type address of copied object
        copied.getAddress().setCity("Geneva");
        copied.getAddress().setCountry("Switzerland");
        copied.getAddress().setPostcode(1201);

        // Check If field type equal without modification
        assertNotEquals(original.getUserId(), copied.getUserId());
        assertNotEquals(original.getUsername(), copied.getUsername());
        assertNotEquals(original.getAge(), copied.getAge());

        // It changed the original address also
        assertEquals(original.getAddress().getCity(), copied.getAddress().getCity());
        assertEquals(original.getAddress().getCountry(), copied.getAddress().getCountry());
        assertEquals(original.getAddress().getPostcode(), copied.getAddress().getPostcode());
    }

}

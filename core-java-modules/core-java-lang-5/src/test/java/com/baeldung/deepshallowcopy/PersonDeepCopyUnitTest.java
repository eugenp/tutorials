package com.baeldung.deepshallowcopy;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PersonDeepCopyUnitTest {

    @Test
    void whenDeepCopyUsingSerialization() throws IOException, ClassNotFoundException {
        Address address = new Address("Dhaka", "Bangladesh", 1840);
        // original
        Person original = new Person(1L, "Nabil", 25, address);
        // copy
        Person copied = original.deepCopy(); // using deep copy with Serializable

        assertNotEquals(original, copied); // objects are not same
        assertNotEquals(original.getAddress(), copied.getAddress()); // now original and copied object has different address objects
    }

    @Test
    void whenDeepCopyUsingSerializationDetailed() throws IOException, ClassNotFoundException {
        Address address = new Address("Dhaka", "Bangladesh", 1840);
        // original
        Person original = new Person(1L, "Nabil", 25, address);
        // copied
        Person copied = (Person) original.deepCopy();

        assertNotEquals(original, copied); // objects should not be same
        assertNotEquals(original.getAddress(), copied.getAddress()); // different address objects

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

        // Now they have different address objects
        assertNotEquals(original.getAddress().getCity(), copied.getAddress().getCity());
        assertNotEquals(original.getAddress().getCountry(), copied.getAddress().getCountry());
        assertNotEquals(original.getAddress().getPostcode(), copied.getAddress().getPostcode());
    }

}

package com.baeldung.deepshallowcopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void givenOriginalPerson_whenCloned_thenFieldsAreEqualReferences() {
        // Create an Address object
        Address address = new Address("New York", "5th Avenue");

        // Create an original Person object with the Address object
        Person original = new Person("John", 30, address);

        try {
            // Clone the original Person object to create a shallow copy
            Person copy = (Person) original.clone();

            // Verify that the fields of the original and copied Person objects are the same
            assertEquals(original.name, copy.name);
            assertEquals(original.age, copy.age);
            assertSame(original.address, copy.address); // Shallow copy: Same reference for Address
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenOriginalPerson_whenModified_thenClonedPersonReflectsChanges() {
        // Create an Address object
        Address address = new Address("New York", "5th Avenue");

        // Create an original Person object with the Address object
        Person original = new Person("John", 30, address);

        try {
            // Clone the original Person object to create a shallow copy
            Person copy = (Person) original.clone();

            // Modify the original Person object
            original.name = "Doe";
            original.age = 25;
            original.address.city = "Los Angeles";

            // Verify primitive types are not impacted
            assertEquals("John", copy.name);
            assertEquals(30, copy.age);

            // Verify that the shallow copy reflects the changes made to the original object
            assertEquals("Los Angeles", copy.address.city);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

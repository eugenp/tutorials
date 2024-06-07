package com.baeldung.deepshallowcopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void givenOriginalEmployee_whenCloned_thenFieldsAreDeepCopied() {
        // Create an Address object
        Address address = new Address("New York", "5th Avenue");

        // Create an original Employee object with the Address object
        Employee original = new Employee("John", 30, address);

        try {
            // Clone the original Employee object to create a deep copy
            Employee copy = (Employee) original.clone();

            // Verify that the fields of the original and copied Employee objects are deep copied
            assertEquals(original.name, copy.name);
            assertEquals(original.age, copy.age);
            assertNotSame(original.address, copy.address); // Deep copy: Different reference for Address
            assertEquals(original.address.city, copy.address.city);
            assertEquals(original.address.street, copy.address.street);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenOriginalEmployee_whenModified_thenClonedEmployeeDoesNotReflectChanges() {
        // Create an Address object
        Address address = new Address("New York", "5th Avenue");

        // Create an original Employee object with the Address object
        Employee original = new Employee("John", 30, address);

        try {
            // Clone the original Employee object to create a deep copy
            Employee copy = (Employee) original.clone();

            // Modify the original Employee object
            original.name = "Doe";
            original.age = 25;
            original.address.city = "Los Angeles";

            // Verify that the deep copy does not reflect the changes made to the original object
            assertNotEquals("Doe", copy.name);
            assertNotEquals(25, copy.age);
            assertNotEquals("Los Angeles", copy.address.city);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

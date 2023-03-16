package com.baeldung.copy.shallowanddeep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class DeepCopyTest {

    @Test
    public void whenChangingOriginalObject_thenDuplicateShouldNotChange() throws CloneNotSupportedException {
        Address address = new Address("123 Main St", "Kampala");
        Employee original = new Employee("Janet Jesca", 29, address);
        Employee copy = (Employee) original.clone();

        assertNotSame(original, copy);
        assertNotSame(original.getAddress(), copy.getAddress());

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getAddress()
            .getStreet(),
            copy.getAddress()
                .getStreet());
        assertEquals(original.getAddress()
            .getCity(),
            copy.getAddress()
                .getCity());

        original.getAddress()
            .setStreet("Plot 25, Munyonyo");
        assertEquals("123 Main St", copy.getAddress()
            .getStreet());
    }
}

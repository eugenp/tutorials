package com.baeldung.cloning.deep;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DeepCloningTest {

    Person alex = new Person("Alex", "Jones", new Address("Main Street", "Main City"));

    @Test
    public void whenUsingDeepCopy_thenReferencesAreNotTheSame()
    {
        Person constructorCopyOfAlex = new Person(alex);

        assertEquals(alex, constructorCopyOfAlex);

        alex.setAddress(new Address("Unknown Street", "Unknown City"));

        assertNotEquals(alex.getAddress(), constructorCopyOfAlex.getAddress());
    }
}

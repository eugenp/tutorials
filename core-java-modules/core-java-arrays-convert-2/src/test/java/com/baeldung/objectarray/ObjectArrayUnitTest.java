package com.baeldung.objectarray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectArrayUnitTest {

    @Test
    void givenObjectArray_whenHoldingDifferentTypes_thenCorrect() {
        Object[] values = ObjectArray.createSampleArray();

        assertEquals("Hello", values[0]);                   // String stored
        assertEquals(42, values[1]);                        // Integer stored
        assertEquals(3.14, values[2]);                      // Double stored
        assertTrue(values[3] instanceof int[]);             // Array stored
        assertTrue(values[4] instanceof ObjectArray.Person);// Custom class stored
    }

    @Test
    void givenObjectArray_whenAccessingPerson_thenCorrect() {
        Object[] values = ObjectArray.createSampleArray();

        ObjectArray.Person person = (ObjectArray.Person) values[4];
        assertEquals("Alice", person.getName());           // Name matches
        assertEquals(30, person.getAge());                 // Age matches
    }

    @Test
    void givenObjectArray_whenCastingIncorrectly_thenClassCastException() {
        Object[] values = ObjectArray.createSampleArray();

        assertThrows(ClassCastException.class, () -> {
            String wrongCast = (String) values[1];         // values[1] is actually Integer
        });
    }
}

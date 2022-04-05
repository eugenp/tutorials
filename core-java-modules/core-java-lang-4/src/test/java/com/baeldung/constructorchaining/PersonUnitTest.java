package com.baeldung.constructorchaining;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonUnitTest {

    @Test
    public void givenNameLastNameAndAge_whenUsingDedicatedConstructor_shouldInitializeFieldsAndNullifyMiddleName() {
        Person mark = new Person("Mark", "Johnson", 23);

        assertEquals(23, mark.getAge());
        assertEquals("Mark", mark.getFirstName());
        assertEquals("Johnson", mark.getLastName());
        assertNull(mark.getMiddleName());
    }

    @Test
    public void givenAllFieldsRequired_whenUsingDedicatedConstructor_shouldInitializeAllFields() {
        Person mark = new Person("Mark", "Andrew", "Johnson", 23);

        assertEquals(23, mark.getAge());
        assertEquals("Mark", mark.getFirstName());
        assertEquals("Andrew", mark.getMiddleName());
        assertEquals("Johnson", mark.getLastName());
    }
}
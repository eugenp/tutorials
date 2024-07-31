package com.baeldung.constructorchaining;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CustomerUnitTest {

    @Test
    public void givenNameLastNameAndAge_whenUsingDedicatedConstructor_shouldInitializeFieldsAndNullifyMiddleName() {
        Customer mark = new Customer("Mark", "Johnson", 23, "abcd1234");

        assertEquals(23, mark.getAge());
        assertEquals("Mark", mark.getFirstName());
        assertEquals("Johnson", mark.getLastName());
        assertEquals("abcd1234", mark.getLoyaltyCardId());
        assertNull(mark.getMiddleName());
    }

    @Test
    public void givenAllFieldsRequired_whenUsingDedicatedConstructor_shouldInitializeAllFields() {
        Customer mark = new Customer("Mark", "Andrew", "Johnson", 23, "abcd1234");

        assertEquals(23, mark.getAge());
        assertEquals("Mark", mark.getFirstName());
        assertEquals("Andrew", mark.getMiddleName());
        assertEquals("Johnson", mark.getLastName());
        assertEquals("abcd1234", mark.getLoyaltyCardId());
    }
}
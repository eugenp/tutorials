package com.baeldung.junit4;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

@DisplayName("Test case for assertions")
public class AssertionUnitTest {

    @Test
    @DisplayName("Arrays should be equals")
    public void whenAssertingArraysEquality_thenEqual() {
        char[] expected = {'J', 'u', 'p', 'i', 't', 'e', 'r'};
        char[] actual = "Jupiter".toCharArray();

        assertArrayEquals("Arrays should be equal", expected, actual);
    }

    @Test
    public void givenMultipleAssertion_whenAssertingAll_thenOK() {
        assertEquals("4 is 2 times 2", 4, 2 * 2);
        assertEquals("java", "JAVA".toLowerCase());
        assertEquals("null is equal to null", null, null);
    }

    @Test
    public void testAssertThatHasItems() {
        assertThat(Arrays.asList("Java", "Kotlin", "Scala"), hasItems("Java", "Kotlin"));
    }
}

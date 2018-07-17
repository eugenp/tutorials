package com.baeldung;

import static java.time.Duration.ofSeconds;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit test that demonstrate the different assertions available within JUnit 4
 */
public class AssertionUnitTest {

    @Test
    public void whenAssertingArraysEquality_thenEqual() {
        char[] expected = {'J', 'u', 'p', 'i', 't', 'e', 'r'};
        char[] actual = "Jupiter".toCharArray();

        assertArrayEquals(expected, actual, "Arrays should be equal");
    }

    @Test
    public void whenAssertingEquality_thenEqual() {
        float square = 2 * 2;
        float rectangle = 2 * 2;

        assertEquals(square, rectangle);
    }

    @Test
    public void whenAssertingEqualityWithDelta_thenEqual() {
        float square = 2 * 2;
        float rectangle = 3 * 2;
        float delta = 2;

        assertEquals(square, rectangle, delta);
    }

    @Test
    public void whenAssertingConditions_thenVerified() {
        assertTrue(5 > 4, "5 is greater the 4");
        assertTrue(null == null, "null is equal to null");
    }

    @Test
    public void whenAssertingNull_thenTrue() {
        Object cat = null;

        assertNull(cat, () -> "The cat should be null");
    }

    @Test
    public void whenAssertingNotNull_thenTrue() {
        Object dog = new Object();

        assertNotNull(dog, () -> "The dog should not be null");
    }

    @Test
    public void whenAssertingSameObject_thenSuccessfull() {
        String language = "Java";
        Optional<String> optional = Optional.of(language);

        assertSame(language, optional.get());
    }

    @Test
    public void givenBooleanSupplier_whenAssertingCondition_thenVerified() {
        BooleanSupplier condition = () -> 5 > 6;

        assertFalse(condition, "5 is not greater then 6");
    }

    @Test
    @Disabled
    public void whenFailingATest_thenFailed() {
        // Test not completed
        fail("FAIL - test not completed");
    }

    @Test
    public void givenMultipleAssertion_whenAssertingAll_thenOK() {
        assertAll(
           "heading",
           () -> assertEquals(4, 2 * 2, "4 is 2 times 2"),
           () -> assertEquals("java", "JAVA".toLowerCase()),
           () -> assertEquals(null, null, "null is equal to null")
        );
    }

    @Test
    public void givenTwoLists_whenAssertingIterables_thenEquals() {
        Iterable<String> al = new ArrayList<>(asList("Java", "Junit", "Test"));
        Iterable<String> ll = new LinkedList<>(asList("Java", "Junit", "Test"));

        assertIterableEquals(al, ll);
    }

    @Test
    public void whenAssertingTimeout_thenNotExceeded() {
        assertTimeout(
          ofSeconds(2),
          () -> {
            // code that requires less then 2 minutes to execute
            Thread.sleep(1000);
          }
        );
    }

    @Test
    public void whenAssertingTimeoutPreemptively_thenNotExceeded() {
        assertTimeoutPreemptively(
          ofSeconds(2),
          () -> {
            // code that requires less then 2 minutes to execute
            Thread.sleep(1000);
          }
        );
    }

    @Test
    public void whenAssertingEquality_thenNotEqual() {
        Integer value = 5; // result of an algorithm

        assertNotEquals(0, value, "The result cannot be 0");
    }

    @Test
    public void whenAssertingEqualityListOfStrings_thenEqual() {
        List<String> expected = asList("Java", "\\d+", "JUnit");
        List<String> actual = asList("Java", "11", "JUnit");

        assertLinesMatch(expected, actual);
    }

    @Test
    void whenAssertingException_thenThrown() {
        Throwable exception = assertThrows(
          IllegalArgumentException.class,
          () -> {
            throw new IllegalArgumentException("Exception message");
          }
        );
        assertEquals("Exception message", exception.getMessage());
    }

    @Test
    public void testConvertToDoubleThrowException() {
        String age = "eighteen";
        assertThrows(NumberFormatException.class, () -> {
            convertToInt(age);
        });

        assertThrows(NumberFormatException.class, () -> {
            convertToInt(age);
        });
    }

    private static Integer convertToInt(String str) {
        if (str == null) {
            return null;
        }
        return Integer.valueOf(str);
    }

}

package com.baeldung.array.compare;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class CompareByteArraysUnitTest {
    private final static String INPUT = "I am a magic string.";
    private final static byte[] ARRAY1 = INPUT.getBytes();
    private final static byte[] ARRAY2 = INPUT.getBytes();

    @Test
    void whenUsingEqualsSign_thenTwoArraysAreNotEqual() {
        assertFalse(ARRAY1 == ARRAY2);
    }

    @Test
    void whenUsingEquals_thenTwoArraysAreNotEqual() {
        assertFalse(ARRAY1.equals(ARRAY2));
    }

    @Test
    void whenUsingArrayEquals_thenTwoArraysAreEqual() {
        assertTrue(Arrays.equals(ARRAY1, ARRAY2));
    }

    @Test
    void whenComparingStringArrays_thenGetExpectedResult() {
        String[] strArray1 = new String[] { "Java", "is", "great" };
        String[] strArray2 = new String[] { "Java", "is", "great" };

        assertFalse(strArray1 == strArray2);
        assertFalse(strArray1.equals(strArray2));
        assertTrue(Arrays.equals(strArray1, strArray2));
    }
}
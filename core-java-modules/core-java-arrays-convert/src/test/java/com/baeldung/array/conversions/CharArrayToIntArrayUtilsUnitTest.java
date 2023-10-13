package com.baeldung.array.conversions;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class CharArrayToIntArrayUtilsUnitTest {

    @Test
    void givenCharArray_whenUsingGetNumericValueMethod_shouldGetIntArray() {
        int[] expected = { 2, 3, 4, 5 };
        char[] chars = { '2', '3', '4', '5' };
        int[] result = CharArrayToIntArrayUtils.usingGetNumericValueMethod(chars);

        assertArrayEquals(expected, result);
    }

    @Test
    void givenCharArray_whenUsingDigitMethod_shouldGetIntArray() {
        int[] expected = { 1, 2, 3, 6 };
        char[] chars = { '1', '2', '3', '6' };
        int[] result = CharArrayToIntArrayUtils.usingDigitMethod(chars);

        assertArrayEquals(expected, result);
    }

    @Test
    void givenCharArray_whenUsingStreamApi_shouldGetIntArray() {
        int[] expected = { 9, 8, 7, 6 };
        char[] chars = { '9', '8', '7', '6' };
        int[] result = CharArrayToIntArrayUtils.usingStreamApiMethod(chars);

        assertArrayEquals(expected, result);
    }

    @Test
    void givenCharArray_whenUsingParseIntMethod_shouldGetIntArray() {
        int[] expected = { 9, 8, 7, 6 };
        char[] chars = { '9', '8', '7', '6' };
        int[] result = CharArrayToIntArrayUtils.usingParseIntMethod(chars);

        assertArrayEquals(expected, result);
    }

    @Test
    void givenCharArray_whenUsingArraysSetAllMethod_shouldGetIntArray() {
        int[] expected = { 4, 9, 2, 3 };
        char[] chars = { '4', '9', '2', '3' };
        int[] result = CharArrayToIntArrayUtils.usingArraysSetAllMethod(chars);

        assertArrayEquals(expected, result);
    }

}

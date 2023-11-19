package com.baeldung.array.initbooleanarray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class InitBooleanArrayUnitTest {
    @Test
    void whenInitPrimitiveBooleanArray_shouldGetExpectedArray() {
        boolean[] expected = { false, false, false, false, false };
        boolean[] myArray = new boolean[5];
        assertArrayEquals(expected, myArray);
    }

    @Test
    void whenInitPrimitiveBooleanArrayAndUseArrayFill_shouldGetExpectedArray() {
        boolean[] expected = { true, true, true, true, true };
        boolean[] myArray = new boolean[5];
        Arrays.fill(myArray, true);
        assertArrayEquals(expected, myArray);
    }

    @Test
    void whenInitBooleanArray_shouldGetExpectedArray() {
        Boolean[] expectedAllNull = { null, null, null, null, null };
        Boolean[] myNullArray = new Boolean[5];
        assertArrayEquals(expectedAllNull, myNullArray);

        Boolean[] expectedAllFalse = { false, false, false, false, false };
        Boolean[] myFalseArray = new Boolean[5];
        Arrays.fill(myFalseArray, false);
        assertArrayEquals(expectedAllFalse, myFalseArray);

        Boolean[] expectedAllTrue = { true, true, true, true, true };
        Boolean[] myTrueArray = new Boolean[5];
        Arrays.fill(myTrueArray, true);
        assertArrayEquals(expectedAllTrue, myTrueArray);
    }
}

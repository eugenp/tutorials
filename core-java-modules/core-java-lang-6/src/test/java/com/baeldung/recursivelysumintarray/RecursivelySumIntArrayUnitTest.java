package com.baeldung.recursivelysumintarray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecursivelySumIntArrayUnitTest {

    private static final int[] INT_ARRAY = { 1, 2, 3, 4, 5 };

    static int sumIntArray1(int[] array) {
        if (array.length == 1) {
            return array[0];
        } else {
            return array[0] + sumIntArray1(Arrays.copyOfRange(array, 1, array.length));
        }
    }

    static int sumIntArray2(int[] array, int index) {
        if (index == 0) {
            return array[index];
        } else {
            return array[index] + sumIntArray2(array, index - 1);
        }
    }

    @Test
    void whenUsingSumIntArray1_thenGetExpectedResult() {
        assertEquals(15, sumIntArray1(INT_ARRAY));
    }

    @Test
    void whenUsingSumIntArray2_thenGetExpectedResult() {
        assertEquals(15, sumIntArray2(INT_ARRAY, INT_ARRAY.length - 1));
    }
}
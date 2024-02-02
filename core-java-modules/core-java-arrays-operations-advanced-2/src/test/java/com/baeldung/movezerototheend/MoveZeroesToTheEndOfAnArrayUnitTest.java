package com.baeldung.movezerototheend;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MoveZeroesToTheEndOfAnArrayUnitTest {
    static final int[] EXPECTED = new int[] { 1, 2, 3, 4, 0, 0 };

    @Test
    void whenCreatingANewArrayAndCopyingValues_thenGetTheExpectedResult() {
        int[] array = new int[] { 1, 2, 0, 3, 4, 0 };
        int[] result = new int[array.length];
        int idx = 0;
        for (int n : array) {
            if (n != 0) {
                result[idx++] = n;
            }
        }
        assertArrayEquals(EXPECTED, result);
    }

    @Test
    void whenMovingZeroInTheOriginalArray_thenGetTheExpectedResult() {
        int[] array = new int[] { 1, 2, 0, 3, 4, 0 };
        int idx = 0;
        for (int n : array) {
            if (n != 0) {
                array[idx++] = n;
            }
            System.out.println(Arrays.toString(array));
        }
        while (idx < array.length) {
            array[idx++] = 0;
        }
        assertArrayEquals(EXPECTED, array);
    }
}
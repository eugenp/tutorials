package com.baeldung.algorithms.counting;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class CountingSortUnitTest {

    @Test
    void countElements_GivenAnArray_ShouldCalculateTheFrequencyArrayAsExpected() {
        int k = 5;
        int[] input = { 4, 3, 2, 5, 4, 3, 5, 1, 0, 2, 5 };

        int[] c = CountingSort.countElements(input, k);
        int[] expected = { 1, 2, 4, 6, 8, 11 };
        assertArrayEquals(expected, c);
    }

    @Test
    void sort_GivenAnArray_ShouldSortTheInputAsExpected() {
        int k = 5;
        int[] input = { 4, 3, 2, 5, 4, 3, 5, 1, 0, 2, 5 };

        int[] sorted = CountingSort.sort(input, k);

        // Our sorting algorithm and Java's should return the same result
        Arrays.sort(input);
        assertArrayEquals(input, sorted);
    }
}
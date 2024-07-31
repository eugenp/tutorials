package com.baeldung.algorithms.insertionsort;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class InsertionSortUnitTest {

    @Test
    void givenUnsortedArray_whenInsertionSortImperative_thenSortedAsc() {
        int[] input = {6, 2, 3, 4, 5, 1};
        InsertionSort.insertionSortImperative(input);
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, input, "the two arrays are not equal");
    }

    @Test
    void givenUnsortedArray_whenInsertionSortRecursive_thenSortedAsc() {
        int[] input = {6, 4, 5, 2, 3, 1};
        InsertionSort.insertionSortRecursive(input);
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals( expected, input, "the two arrays are not equal");
    }
}

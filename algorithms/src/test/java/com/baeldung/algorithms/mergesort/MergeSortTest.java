package com.baeldung.algorithms.mergesort;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class MergeSortTest {

    @Test
    void positiveTest() {
        int[] input = { 5, 1, 6, 2, 3, 4 };
        int[] expected = { 1, 2, 3, 4, 5, 6 };
        MergeSort.mergeSort(input, input.length);
        assertArrayEquals(expected, input);
    }
    
}

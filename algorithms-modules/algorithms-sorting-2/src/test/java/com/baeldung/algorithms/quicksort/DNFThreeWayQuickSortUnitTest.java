package com.baeldung.algorithms.quicksort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class DNFThreeWayQuickSortUnitTest {

    @Test
    void givenIntegerArray_whenSortedWithThreeWayQuickSort_thenGetSortedArray() {
        int[] actual = {3, 5, 5, 5, 3, 7, 7, 3, 5, 5, 7, 3, 3};
        int[] expected = {3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 7, 7, 7};
        DutchNationalFlagPartioning.quicksort(actual, 0, actual.length - 1);
        assertArrayEquals(expected, actual);
    }
}

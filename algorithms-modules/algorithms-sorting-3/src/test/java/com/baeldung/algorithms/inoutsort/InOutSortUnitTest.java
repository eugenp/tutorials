package com.baeldung.algorithms.inoutsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class InOutSortUnitTest {

    @Test
    void givenArray_whenInPlaceSort_thenReversed() {
        int[] input = {1, 2, 3, 4, 5, 6, 7};
        int[] expected = {7, 6, 5, 4, 3, 2, 1};
        assertArrayEquals(expected, InOutSort.reverseInPlace(input), "the two arrays are not equal");
    }

    @Test
     void givenArray_whenOutOfPlaceSort_thenReversed() {
        int[] input = {1, 2, 3, 4, 5, 6, 7};
        int[] expected = {7, 6, 5, 4, 3, 2, 1};
        assertArrayEquals(expected, InOutSort.reverseOutOfPlace(input), "the two arrays are not equal");
    }
}

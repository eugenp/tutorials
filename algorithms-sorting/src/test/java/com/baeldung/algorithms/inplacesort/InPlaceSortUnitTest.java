package com.baeldung.algorithms.inplacesort;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class InPlaceSortUnitTest {

    @Test
    public void givenArray_whenInPlaceSort_thenReversed() {
        int[] input = {1, 2, 3, 4, 5, 6, 7};
        int[] expected = {7, 6, 5, 4, 3, 2, 1};
        assertArrayEquals("the two arrays are not equal", expected, InPlaceSort.reverseInPlace(input));
    }

    @Test
    public void givenArray_whenOutOfPlaceSort_thenReversed() {
        int[] input = {1, 2, 3, 4, 5, 6, 7};
        int[] expected = {7, 6, 5, 4, 3, 2, 1};
        assertArrayEquals("the two arrays are not equal", expected, InPlaceSort.reverseOutOfPlace(input));
    }
}

package com.baeldung.algorithms.sortedarrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class SortedArraysUnitTest {

    @Test
    public void givenTwoSortedArraysWhenMergeThenReturnMergedSortedArray() {

        int[] first = { 3, 7 };
        int[] second = { 4, 8, 11 };
        int[] result = { 3, 4, 7, 8, 11 };

        assertArrayEquals(result, SortedArrays.merge(first, second));
    }

    @Test
    public void givenTwoSortedArraysWithDuplicatesWhenMergeThenReturnMergedSortedArray() {

        int[] first = { 3, 3, 7 };
        int[] second = { 4, 8, 8, 11 };
        int[] result = { 3, 3, 4, 7, 8, 8, 11 };

        assertArrayEquals(result, SortedArrays.merge(first, second));
    }
}

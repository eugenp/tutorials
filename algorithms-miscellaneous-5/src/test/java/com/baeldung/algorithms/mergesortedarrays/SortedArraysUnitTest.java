package com.baeldung.algorithms.mergesortedarrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.mergesortedarrays.SortedArrays;

public class SortedArraysUnitTest {

    @Test
    public void givenTwoSortedArrays_whenMerged_thenReturnMergedSortedArray() {

        int[] foo = { 3, 7 };
        int[] bar = { 4, 8, 11 };
        int[] merged = { 3, 4, 7, 8, 11 };

        assertArrayEquals(merged, SortedArrays.merge(foo, bar));
    }

    @Test
    public void givenTwoSortedArraysWithDuplicates_whenMerged_thenReturnMergedSortedArray() {

        int[] foo = { 3, 3, 7 };
        int[] bar = { 4, 8, 8, 11 };
        int[] merged = { 3, 3, 4, 7, 8, 8, 11 };

        assertArrayEquals(merged, SortedArrays.merge(foo, bar));
    }
}

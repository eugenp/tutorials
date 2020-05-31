package com.baeldung.algorithms.mergesort;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.quicksort.DutchNationalFlagPartioning;

public class MergeSortUnitTest {

    @Test
    public void givenIntegerArray_whenSortedWithMergeSort_thenGetSortedArray() {
        int[] actual = { 3, 5, 5, 5, 3, 7, 7, 3, 5, 5, 7, 3, 3 };
        int[] expected = { 3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 7, 7, 7 };
        MergeSort.mergeSort(actual, 0, actual.length - 1);
        Assert.assertArrayEquals(expected, actual);
    }

}

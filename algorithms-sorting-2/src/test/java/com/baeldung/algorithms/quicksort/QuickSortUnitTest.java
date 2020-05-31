package com.baeldung.algorithms.quicksort;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.mergesort.MergeSort;

public class QuickSortUnitTest {

    @Test
    public void givenIntegerArray_whenSortedWithQuickSort_thenGetSortedArray() {
        int[] actual = { 3, 5, 5, 5, 3, 7, 7, 3, 5, 5, 7, 3, 3 };
        int[] expected = { 3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 7, 7, 7 };
        MergeSort.mergeSort(actual,  0, actual.length - 1);
        Assert.assertArrayEquals(expected, actual);
    }

}

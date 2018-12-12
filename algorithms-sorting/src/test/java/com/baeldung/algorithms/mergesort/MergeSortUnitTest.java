package com.baeldung.algorithms.mergesort;

import org.junit.Assert;

import org.junit.Test;

public class MergeSortUnitTest {

    @Test
    public void positiveTest() {
        int[] actual = { 5, 1, 6, 2, 3, 4 };
        int[] expected = { 1, 2, 3, 4, 5, 6 };
        MergeSort.mergeSort(actual, actual.length);
        Assert.assertArrayEquals(expected, actual);
    }

}

package com.baeldung.algorithms.insertionsort;

import com.baeldung.algorithms.insertionsort.InsertionSort;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class InsertionSortUnitTest {

    @Test
    public void givenUnsortedArray_whenInsertionSortImperative_thenSortedAsc() {
        int[] input = {6, 2, 3, 4, 5, 1};
        InsertionSort.insertionSortImperative(input);
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals("the two arrays are not equal", expected, input);
    }

    @Test
    public void givenUnsortedArray_whenInsertionSortRecursive_thenSortedAsc() {
        int[] input = {6, 4, 5, 2, 3, 1};
        InsertionSort.insertionSortRecursive(input);
        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals("the two arrays are not equal", expected, input);
    }
}

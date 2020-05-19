package com.baeldung.algorithms.selectionsort;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class SelectionSortUnitTest {

    @Test
    public void givenUnsortedArray_whenSelectionSort_SortAscending_thenSortedAsc() {
        int[] input = { 5, 4, 1, 6, 2 };
        SelectionSort.sortAscending(input);
        int[] expected = {1, 2, 4, 5, 6};
        assertArrayEquals("the two arrays are not equal", expected, input);
    }
    
    @Test
    public void givenUnsortedArray_whenSelectionSort_SortDescending_thenSortedDesc() {
        int[] input = { 5, 4, 1, 6, 2 };
        SelectionSort.sortDescending(input);
        int[] expected = {6, 5, 4, 2, 1};
        assertArrayEquals("the two arrays are not equal", expected, input);
    }
}

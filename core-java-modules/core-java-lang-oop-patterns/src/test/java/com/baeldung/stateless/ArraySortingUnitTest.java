package com.baeldung.stateless;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class ArraySortingUnitTest {
    
    @Test
    void givenArray_whenBubbleSorting_thenSorted() {
        int[] arrayToSort = {17, 6, 11, 41, 5, 3, 4, -9};
        int[] sortedArray = {-9, 3, 4, 5, 6, 11, 17, 41};
        
        IntArraySorter sorter = new IntArraySorter(new BubbleSort());
        sorter.sort(arrayToSort);
        assertArrayEquals(sortedArray, arrayToSort);
    }
    
    @Test
    void givenArray_whenQuickSortSorting_thenSorted() {
        int[] arrayToSort = {17, 6, 11, 41, 5, 3, 4, -9};
        int[] sortedArray = {-9, 3, 4, 5, 6, 11, 17, 41};
        
        IntArraySorter sorter = new IntArraySorter(new QuickSort());
        sorter.sort(arrayToSort);
        assertArrayEquals(sortedArray, arrayToSort);
    }
}

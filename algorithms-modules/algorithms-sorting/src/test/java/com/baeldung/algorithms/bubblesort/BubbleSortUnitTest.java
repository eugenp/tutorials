package com.baeldung.algorithms.bubblesort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class BubbleSortUnitTest {

    @Test
    void givenIntegerArray_whenSortedWithBubbleSort_thenGetSortedArray() {
        Integer[] array = { 2, 1, 4, 6, 3, 5 };
        Integer[] sortedArray = { 1, 2, 3, 4, 5, 6 };
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.bubbleSort(array);
        assertArrayEquals(array, sortedArray);
    }

    @Test
    void givenIntegerArray_whenSortedWithOptimizedBubbleSort_thenGetSortedArray() {
        Integer[] array = { 2, 1, 4, 6, 3, 5 };
        Integer[] sortedArray = { 1, 2, 3, 4, 5, 6 };
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.optimizedBubbleSort(array);
        assertArrayEquals(array, sortedArray);
    }
}
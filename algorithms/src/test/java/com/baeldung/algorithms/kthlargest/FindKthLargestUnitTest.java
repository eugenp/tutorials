package com.baeldung.algorithms.kthlargest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FindKthLargestUnitTest {

    private FindKthLargest findKthLargest;
    private Integer[] arr = {3, 7, 1, 2, 8, 10, 4, 5, 6, 9};

    @Before
    public void setup() {
        findKthLargest = new FindKthLargest();
    }

    @Test
    public void givenIntArray_whenFindKthLargestBySorting_thenGetResult() {
        int k = 3;
        assertEquals(8, findKthLargest.findKthLargestBySorting(arr, k));
    }

    @Test
    public void givenIntArray_whenFindKthLargestByQuickSelect_thenGetResult() {
        int k = 3;
        int kthLargest = arr.length - k;
        assertEquals(8, findKthLargest.findKthElementByQuickSelect(arr,0,arr.length-1, kthLargest));
    }

    @Test
    public void givenIntArray_whenFindKthSmallestByQuickSelect_thenGetResult() {
        int k = 3;
        assertEquals(3, findKthLargest.findKthElementByQuickSelect(arr,0,arr.length-1, k-1));
    }

    @Test
    public void givenIntArray_whenFindKthLargestByRandomizedQuickSelect_thenGetResult() {
        int k = 3;
        int kthLargest = arr.length - k;
        assertEquals(8, findKthLargest.findKthElementByRandomizedQuickSelect(arr,0,arr.length-1, kthLargest));
    }

}

package com.baeldung.algorithms.kthlargest;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

public class FindKthLargestUnitTest {

    private FindKthLargest findKthLargest;
    private Integer[] arr = { 3, 7, 1, 2, 8, 10, 4, 5, 6, 9 };

    @Before
    public void setup() {
        findKthLargest = new FindKthLargest();
    }

    @Test
    public void givenIntArray_whenFindKthLargestBySorting_thenGetResult() {
        int k = 3;
        assertThat(findKthLargest.findKthLargestBySorting(arr, k)).isEqualTo(8);
    }

    @Test
    public void givenIntArray_whenFindKthLargestBySortingDesc_thenGetResult() {
        int k = 3;
        assertThat(findKthLargest.findKthLargestBySortingDesc(arr, k)).isEqualTo(8);
    }

    @Test
    public void givenIntArray_whenFindKthLargestByQuickSelect_thenGetResult() {
        int k = 3;
        int kthLargest = arr.length - k;
        assertThat(findKthLargest.findKthElementByQuickSelect(arr, 0, arr.length - 1, kthLargest)).isEqualTo(8);
    }

    @Test
    public void givenIntArray_whenFindKthElementByQuickSelectIterative_thenGetResult() {
        int k = 3;
        int kthLargest = arr.length - k;
        assertThat(findKthLargest.findKthElementByQuickSelectWithIterativePartition(arr, 0, arr.length - 1, kthLargest)).isEqualTo(8);
    }

    @Test
    public void givenIntArray_whenFindKthSmallestByQuickSelect_thenGetResult() {
        int k = 3;
        assertThat(findKthLargest.findKthElementByQuickSelect(arr, 0, arr.length - 1, k - 1)).isEqualTo(3);
    }

    @Test
    public void givenIntArray_whenFindKthLargestByRandomizedQuickSelect_thenGetResult() {
        int k = 3;
        int kthLargest = arr.length - k;
        assertThat(findKthLargest.findKthElementByRandomizedQuickSelect(arr, 0, arr.length - 1, kthLargest)).isEqualTo(8);
    }

}

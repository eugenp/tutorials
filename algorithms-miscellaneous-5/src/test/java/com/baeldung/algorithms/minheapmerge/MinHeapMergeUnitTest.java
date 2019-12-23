package com.baeldung.algorithms.minheapmerge;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MinHeapMergeUnitTest {

    private final int[][] inputArray = { { 0, 6 }, { 1, 5, 10, 100 }, { 2, 4, 200, 650 } };
    private final int[] expectedArray = { 0, 1, 2, 4, 5, 6, 10, 100, 200, 650 };

    @Test
    public void givenSortedArrays_whenMerged_thenShouldReturnASingleSortedarray() {
        int[] resultArray = MinHeapMerge.merge(inputArray);
        
        assertThat(resultArray.length, is(equalTo(10)));
        assertThat(resultArray, is(equalTo(expectedArray)));
    }
    
    @Test
    public void givenEmptyHeapNodesAndInputArray_whenPopulated_thenShouldPopulateAndReturnResultArrayLength() {
        int size = MinHeapMerge.populateHeapNodesAndDetermineResultingArrayLength(inputArray, new HeapNode[inputArray.length]);
        
        assertThat(size, is(equalTo(10)));
    }
    
    @Test
    public void givenArrayAndHeapNodesAndResultArraySize_whenMerged_thenShouldReturnASingleSortedarray() {
        HeapNode[] heapNodes = new HeapNode[inputArray.length];
        int size = MinHeapMerge.populateHeapNodesAndDetermineResultingArrayLength(inputArray, heapNodes);
        
        int[] resultArray = MinHeapMerge.createMinHeapAndMergeArrays(inputArray, heapNodes, size);
        
        assertThat(resultArray.length, is(equalTo(10)));
        assertThat(resultArray, is(equalTo(expectedArray)));
    }
}

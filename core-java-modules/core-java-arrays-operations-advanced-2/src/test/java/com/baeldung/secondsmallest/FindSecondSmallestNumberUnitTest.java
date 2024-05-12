package com.baeldung.secondsmallest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class FindSecondSmallestNumberUnitTest {

    @Test
    public void givenAnArray_whenUsingSorting_thenReturnCorrectSecondSmallestNumber() {
        assertEquals(4, FindSecondSmallestNumber.usingArraySort(new int[] { 5, 3, 8, 9, 6, 8, 4, 4}));
        assertEquals(-1, FindSecondSmallestNumber.usingArraySort(new int[] { 5 }));
        assertEquals(5, FindSecondSmallestNumber.usingArraySort(new int[] { 3, 5}));
        assertEquals(-1, FindSecondSmallestNumber.usingArraySort(new int[] { 5, 5, 5, 5, 5}));
    }

    @Test
    public void givenAnArray_whenUsingSinglePassThrough_thenReturnCorrectSecondSmallestNumber() {
        assertEquals(4, FindSecondSmallestNumber.usingSinglePassThrough(new int[] { 5, 3, 8, 9, 6, 8, 4, 4 }));
        assertEquals(-1, FindSecondSmallestNumber.usingSinglePassThrough(new int[] { 5 }));
        assertEquals(5, FindSecondSmallestNumber.usingSinglePassThrough(new int[] { 3, 5}));
        assertEquals(-1, FindSecondSmallestNumber.usingSinglePassThrough(new int[] { 5, 5, 5, 5, 5}));
    }

    @Test
    public void givenAnArray_whenUsingMinHeap_thenReturnCorrectSecondSmallestNumber() {
        assertEquals(4, FindSecondSmallestNumber.usingMinHeap(new int[] { 5, 3, 8, 9, 6, 8, 4, 4 }));
        assertEquals(-1, FindSecondSmallestNumber.usingMinHeap(new int[] { 5 }));
        assertEquals(5, FindSecondSmallestNumber.usingMinHeap(new int[] { 3, 5}));
        assertEquals(-1, FindSecondSmallestNumber.usingMinHeap(new int[] { 5, 5, 5, 5, 5}));
    }

}

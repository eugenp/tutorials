package com.baeldung.closesttozero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortingAndBinarySearchUnitTest {

    @Test
    void whenFindingClosestToZeroWithBruteForce_thenResultShouldBeCorrect() throws IllegalAccessException {
        int[] arr = {1, 60, -10, 70, -80, 85};
        assertEquals(1, SortingAndBinarySearch.findClosestToZero(arr));
    }

}

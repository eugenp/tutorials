package com.baeldung.algorithms.shellsort;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ShellSortUnitTest {

    @Test
    public void givenUnsortedArray_whenShellSort_thenSortedAsc() {
        int[] input = {41, 15, 82, 5, 65, 19, 32, 43, 8};
        ShellSort.sort(input);
        int[] expected = {5, 8, 15, 19, 32, 41, 43, 65, 82};
        assertArrayEquals("the two arrays are not equal", expected, input);
    }
}

package com.baeldung.algorithms.shellsort;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class ShellSortUnitTest {

    @Test
    void givenUnsortedArray_whenShellSort_thenSortedAsc() {
        int[] input = {41, 15, 82, 5, 65, 19, 32, 43, 8};
        ShellSort.sort(input);
        int[] expected = {5, 8, 15, 19, 32, 41, 43, 65, 82};
        assertArrayEquals( expected, input, "the two arrays are not equal");
    }
}

package com.baeldung.algorithms.smallestinteger;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SmallestMissingPositiveIntegerUnitTest {
    @Test
    void givenArrayWithThreeMissing_whenSearchInSortedArray_thenThree() {
        int[] input = new int[] {0, 1, 2, 4, 5};

        int result = SmallestMissingPositiveInteger.searchInSortedArray(input);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void givenArrayWithOneAndThreeMissing_whenSearchInSortedArray_thenOne() {
        int[] input = new int[] {0, 2, 4, 5};

        int result = SmallestMissingPositiveInteger.searchInSortedArray(input);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void givenArrayWithoutMissingInteger_whenSearchInSortedArray_thenArrayLength() {
        int[] input = new int[] {0, 1, 2, 3, 4, 5};

        int result = SmallestMissingPositiveInteger.searchInSortedArray(input);

        assertThat(result).isEqualTo(input.length);
    }

    @Test
    void givenArrayWithThreeMissing_whenSearchInUnsortedArraySortingFirst_thenThree() {
        int[] input = new int[] {1, 4, 0, 5, 2};

        int result = SmallestMissingPositiveInteger.searchInUnsortedArraySortingFirst(input);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void givenArrayWithOneAndThreeMissing_whenSearchInUnsortedArraySortingFirst_thenOne() {
        int[] input = new int[] {4, 2, 0, 5};

        int result = SmallestMissingPositiveInteger.searchInUnsortedArraySortingFirst(input);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void givenArrayWithoutMissingInteger_whenSearchInUnsortedArraySortingFirst_thenArrayLength() {
        int[] input = new int[] {4, 5, 1, 3, 0, 2};

        int result = SmallestMissingPositiveInteger.searchInUnsortedArraySortingFirst(input);

        assertThat(result).isEqualTo(input.length);
    }

    @Test
    void givenArrayWithThreeMissing_whenSearchInUnsortedArrayBooleanArray_thenThree() {
        int[] input = new int[] {1, 4, 0, 5, 2};

        int result = SmallestMissingPositiveInteger.searchInUnsortedArrayBooleanArray(input);

        assertThat(result).isEqualTo(3);
    }

    @Test
    void givenArrayWithOneAndThreeMissing_whenSearchInUnsortedArrayBooleanArray_thenOne() {
        int[] input = new int[] {4, 2, 0, 5};

        int result = SmallestMissingPositiveInteger.searchInUnsortedArrayBooleanArray(input);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void givenArrayWithoutMissingInteger_whenSearchInUnsortedArrayBooleanArray_thenArrayLength() {
        int[] input = new int[] {4, 5, 1, 3, 0, 2};

        int result = SmallestMissingPositiveInteger.searchInUnsortedArrayBooleanArray(input);

        assertThat(result).isEqualTo(input.length);
    }
}
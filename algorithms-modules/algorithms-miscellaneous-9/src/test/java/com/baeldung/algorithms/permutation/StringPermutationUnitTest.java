package com.baeldung.algorithms.permutation;

import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationInclusion;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithMap;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithOneCounter;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithSorting;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithTwoCounters;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringPermutationUnitTest {

    @Test
    void givenTwoStringsArePermutation_whenSortingCharsArray_thenPermutation() {
        assertTrue(isPermutationWithSorting("baeldung", "luaebngd"));
        assertTrue(isPermutationWithSorting("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenSortingCharsArray_thenNotPermutation() {
        assertFalse(isPermutationWithSorting("baeldung", "luaebgd"));
        assertFalse(isPermutationWithSorting("baeldung", "luaebngq"));
    }

    @Test
    void givenTwoStringsArePermutation_whenTwoCountersCharsFrequencies_thenPermutation() {
        assertTrue(isPermutationWithTwoCounters("baeldung", "luaebngd"));
        assertTrue(isPermutationWithTwoCounters("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenTwoCountersCharsFrequencies_thenNotPermutation() {
        assertFalse(isPermutationWithTwoCounters("baeldung", "luaebgd"));
        assertFalse(isPermutationWithTwoCounters("baeldung", "luaebngq"));
    }

    @Test
    void givenTwoStringsArePermutation_whenOneCounterCharsFrequencies_thenPermutation() {
        assertTrue(isPermutationWithOneCounter("baeldung", "luaebngd"));
        assertTrue(isPermutationWithOneCounter("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenOneCounterCharsFrequencies_thenNotPermutation() {
        assertFalse(isPermutationWithOneCounter("baeldung", "luaebgd"));
        assertFalse(isPermutationWithOneCounter("baeldung", "luaebngq"));
    }

    @Test
    void givenTwoStringsArePermutation_whenCountCharsFrequenciesWithMap_thenPermutation() {
        assertTrue(isPermutationWithMap("baelduňg", "luaebňgd"));
        assertTrue(isPermutationWithMap("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenCountCharsFrequenciesWithMap_thenNotPermutation() {
        assertFalse(isPermutationWithMap("baelduňg", "luaebgd"));
        assertFalse(isPermutationWithMap("baeldung", "luaebngq"));
        assertFalse(isPermutationWithMap("baeldung", "luaebngg"));
    }

    @Test
    void givenTwoStrings_whenIncludePermutation_thenPermutation() {
        assertTrue(isPermutationInclusion("baeldung", "ea"));
        assertTrue(isPermutationInclusion("baeldung", "ae"));
    }

    @Test
    void givenTwoStrings_whenNotIncludePermutation_thenNotPermutation() {
        assertFalse(isPermutationInclusion("baeldung", "au"));
        assertFalse(isPermutationInclusion("baeldung", "baeldunga"));
    }
}

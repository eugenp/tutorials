package com.baeldung.algorithms.permutation;

import org.junit.jupiter.api.Test;

import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationInclusion;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithMap;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithOneCounter;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithSorting;
import static com.baeldung.algorithms.permutation.StringPermutation.isPermutationWithTwoCounters;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringPermutationTest {

    @Test
    void givenTwoStringsArePermutation_whenSortingCharsArray_thenOk() {
        assertTrue(isPermutationWithSorting("baeldung", "luaebngd"));
        assertTrue(isPermutationWithSorting("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenSortingCharsArray_thenKo() {
        assertFalse(isPermutationWithSorting("baeldung", "luaebgd"));
        assertFalse(isPermutationWithSorting("baeldung", "luaebngq"));
    }

    @Test
    void givenTwoStringsArePermutation_whenTwoCountersCharsFrequencies_thenOk() {
        assertTrue(isPermutationWithTwoCounters("baeldung", "luaebngd"));
        assertTrue(isPermutationWithTwoCounters("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenTwoCountersCharsFrequencies_thenKo() {
        assertFalse(isPermutationWithTwoCounters("baeldung", "luaebgd"));
        assertFalse(isPermutationWithTwoCounters("baeldung", "luaebngq"));
    }

    @Test
    void givenTwoStringsArePermutation_whenOneCounterCharsFrequencies_thenOk() {
        assertTrue(isPermutationWithOneCounter("baeldung", "luaebngd"));
        assertTrue(isPermutationWithOneCounter("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenOneCounterCharsFrequencies_thenKo() {
        assertFalse(isPermutationWithOneCounter("baeldung", "luaebgd"));
        assertFalse(isPermutationWithOneCounter("baeldung", "luaebngq"));
    }

    @Test
    void givenTwoStringsArePermutation_whenCountCharsFrequenciesWithMap_thenOk() {
        assertTrue(isPermutationWithMap("baelduňg", "luaebňgd"));
        assertTrue(isPermutationWithMap("hello world", "world hello"));
    }

    @Test
    void givenTwoStringsAreNotPermutation_whenCountCharsFrequenciesWithMap_thenKo() {
        assertFalse(isPermutationWithMap("baelduňg", "luaebgd"));
        assertFalse(isPermutationWithMap("baeldung", "luaebngq"));
        assertFalse(isPermutationWithMap("baeldung", "luaebngg"));
    }

    @Test
    void givenTwoStrings_whenIncludePermutation_thenOk() {
        assertTrue(isPermutationInclusion("baeldung", "ea"));
        assertTrue(isPermutationInclusion("baeldung", "ae"));
    }

    @Test
    void givenTwoStrings_whenNotIncludePermutation_thenKo() {
        assertFalse(isPermutationInclusion("baeldung", "au"));
        assertFalse(isPermutationInclusion("baeldung", "baeldunga"));
    }
}

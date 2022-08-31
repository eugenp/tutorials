package com.baeldung.algorithms.combinatorics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CombinatoricsUnitTest {

    @Test
    public void givenEmptySequence_whenCallingPermutations_ShouldReturnEmptyList() {
        List<Integer> sequence = Arrays.asList();

        List<List<Integer>> permutations = Combinatorics.permutations(sequence);

        assertEquals(0, permutations.size());
    }

    @Test
    public void givenOneElementSequence_whenCallingPermutations_ShouldReturnPermutations() {
        List<Integer> sequence = Arrays.asList(1);

        List<List<Integer>> permutations = Combinatorics.permutations(sequence);

        assertEquals(1, permutations.size());
        assertEquals(1, permutations.get(0).size());
        assertSame(1, permutations.get(0).get(0));
    }

    @Test
    public void givenFourElementsSequence_whenCallingPermutations_ShouldReturnPermutations() {
        List<Integer> sequence = Arrays.asList(1, 2, 3, 4);

        List<List<Integer>> permutations = Combinatorics.permutations(sequence);

        assertEquals(24, permutations.size());
        assertEquals(24, new HashSet<>(permutations).size());
    }

    @Test
    public void givenTwoElements_whenCalling3Combinations_ShouldReturnEmptyList() {
        List<Integer> set = Arrays.asList(1, 2);

        List<List<Integer>> combinations = Combinatorics.combinations(set, 3);

        assertEquals(0, combinations.size());
    }

    @Test
    public void givenThreeElements_whenCalling3Combinations_ShouldReturnOneCombination() {
        List<Integer> set = Arrays.asList(1, 2, 3);

        List<List<Integer>> combinations = Combinatorics.combinations(set, 3);

        assertEquals(1, combinations.size());
        assertEquals(combinations.get(0), Arrays.asList(1, 2, 3));
    }

    @Test
    public void givenFourElements_whenCalling2Combinations_ShouldReturnCombinations() {
        List<Integer> set = Arrays.asList(1, 2, 3, 4);

        List<List<Integer>> combinations = Combinatorics.combinations(set, 2);

        assertEquals(6, combinations.size());
        assertEquals(6, new HashSet<>(combinations).size());
    }

    @Test
    public void givenFourElements_whenCallingPowerSet_ShouldReturn15Sets() {
        List<Character> sequence = Arrays.asList('a', 'b', 'c', 'd');

        List<List<Character>> combinations = Combinatorics.powerSet(sequence);

        assertEquals(16, combinations.size());
    }
}

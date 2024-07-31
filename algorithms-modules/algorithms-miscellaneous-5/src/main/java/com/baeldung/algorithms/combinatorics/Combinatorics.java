package com.baeldung.algorithms.combinatorics;

import java.util.*;

import static java.util.Collections.swap;

public class Combinatorics {

    public static List<List<Integer>> permutations(List<Integer> sequence) {
        List<List<Integer>> results = new ArrayList<>();
        permutationsInternal(sequence, results, 0);
        return results;
    }

    private static void permutationsInternal(List<Integer> sequence, List<List<Integer>> results, int index) {
        if (index == sequence.size() - 1) {
            results.add(new ArrayList<>(sequence));
        }

        for (int i = index; i < sequence.size(); i++) {
            swap(sequence, i, index);
            permutationsInternal(sequence, results, index + 1);
            swap(sequence, i, index);
        }
    }

    public static List<List<Integer>> combinations(List<Integer> inputSet, int k) {
        List<List<Integer>> results = new ArrayList<>();
        combinationsInternal(inputSet, k, results, new ArrayList<>(), 0);
        return results;
    }

    private static void combinationsInternal(
      List<Integer> inputSet, int k, List<List<Integer>> results, ArrayList<Integer> accumulator, int index) {
        int leftToAccumulate = k - accumulator.size();
        int possibleToAcculumate = inputSet.size() - index;

        if (accumulator.size() == k) {
            results.add(new ArrayList<>(accumulator));
        } else if (leftToAccumulate <= possibleToAcculumate) {
            combinationsInternal(inputSet, k, results, accumulator, index + 1);

            accumulator.add(inputSet.get(index));
            combinationsInternal(inputSet, k, results, accumulator, index + 1);
            accumulator.remove(accumulator.size() - 1);
        }
    }

    public static List<List<Character>> powerSet(List<Character> sequence) {
        List<List<Character>> results = new ArrayList<>();
        powerSetInternal(sequence, results, new ArrayList<>(), 0);
        return results;
    }

    private static void powerSetInternal(
      List<Character> set, List<List<Character>> powerSet, List<Character> accumulator, int index) {
        if (index == set.size()) {
            powerSet.add(new ArrayList<>(accumulator));
        } else {
            accumulator.add(set.get(index));

            powerSetInternal(set, powerSet, accumulator, index + 1);
            accumulator.remove(accumulator.size() - 1);
            powerSetInternal(set, powerSet, accumulator, index + 1);
        }
    }
}

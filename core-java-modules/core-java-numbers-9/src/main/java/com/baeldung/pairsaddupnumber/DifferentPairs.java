package com.baeldung.pairsaddupnumber;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Find all different pairs of numbers in an array that add up to a given sum - Complexity O(n)
 */
public class DifferentPairs {

    /**
     * Show all different pairs using traditional "for" loop
     *
     * @param input - number's array
     * @param sum   - given sum
     * @return - number's array with all existing pairs. This list will contain just one pair's element because
     * the other one can be calculated with SUM - element_1 = element_2
     */
    public static List<Integer> findPairsWithForLoop(int[] input, int sum) {
        final List<Integer> allDifferentPairs = new ArrayList<>();
        // Aux. hash map
        final Map<Integer, Integer> pairs = new HashMap<>();
        for (int i : input) {
            if (pairs.containsKey(i)) {
                if (pairs.get(i) != null) {
                    // Add pair to returned list
                    allDifferentPairs.add(i);
                }
                // Mark pair as added to prevent duplicates
                pairs.put(sum - i, null);
            } else if (!pairs.containsValue(i)) {
                // Add pair to aux. hash map
                pairs.put(sum - i, i);
            }
        }
        return allDifferentPairs;
    }

    /**
     * Show all different pairs using Java 8 stream API
     *
     * @param input - number's array
     * @param sum   - given sum
     * @return - number's array with all existing pairs. This list will contain just one pair's element because
     * the other one can be calculated with SUM - element_1 = element_2
     */
    public static List<Integer> findPairsWithStreamApi(int[] input, int sum) {
        final List<Integer> allDifferentPairs = new ArrayList<>();
        // Aux. hash map
        final Map<Integer, Integer> pairs = new HashMap<>();
        IntStream.range(0, input.length).forEach(i -> {
                    if (pairs.containsKey(input[i])) {
                        if (pairs.get(input[i]) != null) {
                            // Add pair to returned list
                            allDifferentPairs.add(input[i]);
                        }
                        // Mark pair as added to prevent duplicates
                        pairs.put(sum - input[i], null);
                    } else if (!pairs.containsValue(input[i])) {
                        // Add pair to aux. hash map
                        pairs.put(sum - input[i], input[i]);
                    }
                }
        );
        return allDifferentPairs;
    }
}


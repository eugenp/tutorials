package com.baeldung.pairsaddupnumber;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Find all existing pairs of numbers in an array that add up to a given sum - Complexity O(n^2) "Brute force"
 */
public class ExistingPairs {

    /**
     * Show all existing pairs using traditional "for" loop
     *
     * @param input - number's array
     * @param sum   - given sum
     * @return - number's array with all existing pairs. This list will contain just one pair's element because
     * the other one can be calculated with SUM - element_1 = element_2
     */
    public static List<Integer> findPairsWithForLoop(int[] input, int sum) {
        final List<Integer> allExistingPairs = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (j != i && (input[i] + input[j]) == sum) {
                    allExistingPairs.add(input[i]);
                }
            }
        }
        return allExistingPairs;
    }

    /**
     * Show all existing pairs using Java 8 stream API
     *
     * @param input - number's array
     * @param sum   - given sum
     * @return - number's array with all existing pairs. This list will contain just one pair's element because
     * the other one can be calculated with SUM - element_1 = element_2
     */
    public static List<Integer> findPairsWithStreamApi(int[] input, int sum) {
        final List<Integer> allExistingPairs = new ArrayList<>();
        IntStream.range(0, input.length).forEach(i ->
                IntStream.range(0, input.length)
                        .filter(j -> i != j && input[i] + input[j] == sum)
                        .forEach(j -> allExistingPairs.add(input[i]))
        );
        return allExistingPairs;
    }
}


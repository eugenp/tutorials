package com.baeldung.algorithms.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionRecursiveCombinationGenerator {

    private static final int N = 6;
    private static final int R = 3;

    /** 
     * Generate all combinations of r elements from a set
     * @param n - number of elements in input set
     * @param r - number of elements to be chosen
     * @return the list containing all combinations
     */
    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }

    /** 
     * Choose elements from set by recursing over elements selected
     * @param combinations - List to store generated combinations
     * @param data - current combination
     * @param start - starting element of remaining set
     * @param end - last element of remaining set
     * @param index - number of elements chosen so far.
     */
    private void helper(List<int[]> combinations, int data[], int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else {
            int max = Math.min(end, end + 1 - data.length + index);
            for (int i = start; i <= max; i++) {
                data[index] = i;
                helper(combinations, data, i + 1, end, index + 1);
            }
        }
    }

    public static void main(String[] args) {
        SelectionRecursiveCombinationGenerator generator = new SelectionRecursiveCombinationGenerator();
        List<int[]> combinations = generator.generate(N, R);
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
        System.out.printf("generated %d combinations of %d items from %d ", combinations.size(), R, N);
    }
}

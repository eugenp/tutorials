package com.baeldung.algorithms.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetRecursiveCombinationGenerator {

    private static final int N = 5;
    private static final int R = 2;

    /** 
     * Generate all combinations of r elements from a set
     * @param n - number of elements in set
     * @param r - number of elements in selection
     * @return the list containing all combinations
     */
    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n-1, 0);
        return combinations;
    }

    /**
     * @param combinations - List to contain the generated combinations
     * @param data - List of elements in the selection
     * @param start - index of the starting element in the remaining set
     * @param end - index of the last element in the set  
     * @param index - number of elements selected so far
     */
    private void helper(List<int[]> combinations, int data[], int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }

    public static void main(String[] args) {
        SetRecursiveCombinationGenerator generator = new SetRecursiveCombinationGenerator();
        List<int[]> combinations = generator.generate(N, R);
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
        System.out.printf("generated %d combinations of %d items from %d ", combinations.size(), R, N);
    }
}

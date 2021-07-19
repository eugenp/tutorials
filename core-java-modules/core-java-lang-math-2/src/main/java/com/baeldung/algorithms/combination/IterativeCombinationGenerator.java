package com.baeldung.algorithms.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IterativeCombinationGenerator {

    private static final int N = 5;
    private static final int R = 2;

    /**
     * Generate all combinations of r elements from a set
     * @param n the number of elements in input set
     * @param r the number of elements in a combination
     * @return the list containing all combinations
     */
    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    public static void main(String[] args) {
        IterativeCombinationGenerator generator = new IterativeCombinationGenerator();
        List<int[]> combinations = generator.generate(N, R);
        System.out.println(combinations.size());
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }
}

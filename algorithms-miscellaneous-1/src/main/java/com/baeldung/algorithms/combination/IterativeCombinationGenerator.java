package com.baeldung.algorithms.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IterativeCombinationGenerator {
    
    private static final int N = 5;
    private static final int R = 2;

    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            combinations.add(combination.clone());

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

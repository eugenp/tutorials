package com.baeldung.algorithms.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectionRecursiveCombinationGenerator {

    private static final int N = 6;
    private static final int R = 3;

    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }

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
        System.out.println(combinations.size());
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }
}

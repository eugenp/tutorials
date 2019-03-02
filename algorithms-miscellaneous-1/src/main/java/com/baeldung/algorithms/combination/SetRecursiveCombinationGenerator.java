package com.baeldung.algorithms.combination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetRecursiveCombinationGenerator {

    private static final int N = 6;
    private static final int R = 3;

    public List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0, r);
        return combinations;
    }

    private void helper(List<int[]> combinations, int data[], int start, int end, int index, int r) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);

        } else if (start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1, r);
            helper(combinations, data, start + 1, end, index, r);
        }
    }

    public static void main(String[] args) {
        SetRecursiveCombinationGenerator generator = new SetRecursiveCombinationGenerator();
        List<int[]> combinations = generator.generate(N, R);
        System.out.println(combinations.size());
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }
}

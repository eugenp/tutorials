package com.baeldung.algorithms.counting;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CountingSort {

    public static int[] sort(int[] input, int k) {
        verifyPreconditions(input, k);
        if (input.length == 0) return input;

        int[] c = countElements(input, k);
        int[] sorted = new int[input.length];
        for (int i = input.length - 1; i >= 0; i--) {
            int current = input[i];
            sorted[c[current] - 1] = current;
            c[current] -= 1;
        }

        return sorted;
    }

    static int[] countElements(int[] input, int k) {
        int[] c = new int[k + 1];
        Arrays.fill(c, 0);
        for (int i : input) {
            c[i] += 1;
        }

        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }
        return c;
    }

    private static void verifyPreconditions(int[] input, int k) {
        if (input == null) {
            throw new IllegalArgumentException("Input is required");
        }

        int min = IntStream.of(input).min().getAsInt();
        int max = IntStream.of(input).max().getAsInt();

        if (min < 0 || max > k) {
            throw new IllegalArgumentException("The input numbers should be between zero and " + k);
        }
    }
}

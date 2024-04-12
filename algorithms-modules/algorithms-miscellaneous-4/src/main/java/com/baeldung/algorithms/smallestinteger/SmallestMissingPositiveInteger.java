package com.baeldung.algorithms.smallestinteger;

import java.util.Arrays;

public class SmallestMissingPositiveInteger {
    public static int searchInSortedArray(int[] input) {
        for (int i = 0; i < input.length; i++) {
            if (i != input[i]) {
                return i;
            }
        }

        return input.length;
    }

    public static int searchInUnsortedArraySortingFirst(int[] input) {
        Arrays.sort(input);
        return searchInSortedArray(input);
    }

    public static int searchInUnsortedArrayBooleanArray(int[] input) {
        boolean[] flags = new boolean[input.length];
        for (int number : input) {
            if (number < flags.length) {
                flags[number] = true;
            }
        }

        for (int i = 0; i < flags.length; i++) {
            if (!flags[i]) {
                return i;
            }
        }

        return flags.length;
    }
}

package com.baeldung.maxdifference;

public class MaxDifferenceBruteForce {

    public static int[] findMaxDifferenceBruteForce(int[] list) {
        int maxDifference = Integer.MIN_VALUE;
        int indexOne = 0, indexTwo = 0;

        for (int i = 0; i < list.length - 1; i++) {
            for (int j = i + 1; j < list.length; j++) {
                int difference = Math.abs(list[j] - list[i]);
                if (difference > maxDifference) {
                    maxDifference = difference;
                    ;
                    indexOne = i;
                    indexTwo = j;
                }
            }
        }
        return new int[] { indexOne, indexTwo, list[indexOne], list[indexTwo], maxDifference };
    }
}
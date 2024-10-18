package com.baeldung.maxdifference;

public class MaxDifferenceOptimized {

    public static int[] findMaxDifferenceOptimized(int[] list) {
        int minElement = list[0];
        int maxDifference = Integer.MIN_VALUE;
        int indexOne = 0, indexTwo = 0;

        for (int i = 1; i < list.length; i++) {
            if (list[i] - minElement > maxDifference) {
                maxDifference = list[i] - minElement;
                indexTwo = i;
            }
            if (list[i] < minElement) {
                minElement = list[i];
                indexOne = i;
            }
        }
        return new int[] { indexOne, indexTwo, list[indexOne], list[indexTwo], maxDifference };
    }
}
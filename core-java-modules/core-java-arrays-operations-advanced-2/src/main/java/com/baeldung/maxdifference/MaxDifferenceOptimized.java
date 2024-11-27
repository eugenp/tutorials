package com.baeldung.maxdifference;

public class MaxDifferenceOptimized {

    public static int[] findMaxDifferenceOptimized(int[] list) {
        int minElement = list[0];
        int maxElement = list[0];
        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 1; i < list.length; i++) {
            if (list[i] < minElement) {
                minElement = list[i];
                minIndex = i;
            }
            if (list[i] > maxElement) {
                maxElement = list[i];
                maxIndex = i;
            }
        }

        int maxDifference = Math.abs(maxElement - minElement);
        int[] result = new int[] { minIndex, maxIndex, list[minIndex], list[maxIndex], maxDifference };
        return result;
    }
}
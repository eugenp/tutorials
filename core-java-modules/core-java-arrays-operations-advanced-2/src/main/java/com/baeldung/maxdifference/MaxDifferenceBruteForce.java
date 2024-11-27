package com.baeldung.maxdifference;

public class MaxDifferenceBruteForce {

    public static int[] findMaxDifferenceBruteForce(int[] list) {
        int maxDifference = Integer.MIN_VALUE;
        int minIndex = 0, maxIndex = 0;

        for (int i = 0; i < list.length - 1; i++) {
            for (int j = i + 1; j < list.length; j++) {
                int difference = Math.abs(list[j] - list[i]);
                if (difference > maxDifference) {
                    maxDifference = difference;
                    minIndex = i;
                    maxIndex = j;
                }
            }
        }
        int[] result = new int[] { minIndex, maxIndex, list[minIndex], list[maxIndex], maxDifference };
        return result;
    }
}
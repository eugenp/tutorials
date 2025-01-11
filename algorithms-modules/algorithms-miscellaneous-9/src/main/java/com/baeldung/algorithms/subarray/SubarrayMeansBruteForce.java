package com.baeldung.algorithms.subarray;

public class SubarrayMeansBruteForce {

    public static int countSubarraysWithMean(int[] inputArray, int mean) {
        int count = 0;
        for (int i = 0; i < inputArray.length; i++) {
            long sum = 0;
            for (int j = i; j < inputArray.length; j++) {
                sum += inputArray[j];
                if (sum * 1.0 / (j - i + 1) == mean) {
                    count++;
                }
            }
        }
        return count;
    }
}


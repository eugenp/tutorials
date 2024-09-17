package com.baeldung.array.comparing2Darrays;

public class OptimizedApproach {

    public static boolean areArraysEqual(int[][] arr1, int[][] arr2, double similarityThreshold, double samplingWeight) {

        if (arr1 == null || arr2 == null || arr1.length != arr2.length ||
            arr1[0].length != arr2[0].length || samplingWeight <= 0 || samplingWeight > 1) {
            return false;
        }

        int similarElements = 0;
        int checkedElements = 0;

        // Calculate sampling step based on the sampling weight
        int step = Math.max(1, (int)(1 / samplingWeight));

        // Iterate through the arrays using the calculated step
        for (int i = 0; i < arr1.length; i += step) {
            for (int j = 0; j < arr1[0].length; j += step) {
                if (Math.abs(arr1[i][j] - arr2[i][j]) <= 1) {
                    similarElements++;
                }
                checkedElements++;
            }
        }

        // Calculate similarity ratio and compare with the threshold
        return (double) similarElements / checkedElements >= similarityThreshold;
    }
}


package com.baeldung.array.comparing2Darrays;

public class NaiveApproach {

    public static boolean areArraysEqual(int[][] arr1, int[][] arr2) {

        if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            // check if rows have different lengths
            if (arr1[i] == null || arr2[i] == null || arr1[i].length != arr2[i].length) {
                return false;
            }

            for (int j = 0; j < arr1[i].length; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

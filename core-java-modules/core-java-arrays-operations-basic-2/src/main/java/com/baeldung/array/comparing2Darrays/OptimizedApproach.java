package com.baeldung.array.comparing2Darrays;

import java.util.Arrays;

public class OptimizedApproach {

    public static boolean areArraysEqual(int[][] arr1, int[][] arr2) {

        if (arr1 == arr2) return true;
        if (arr1 == null || arr2 == null || arr1.length != arr2.length) return false;
        if (arr1.length == 0) return true;

        int m = arr1.length;
        int n = arr1[0].length;

        if (arr2[0].length != n) return false;

        for (int i = 0; i < m; i++) {
            if (arr1[i].length != n || arr2[i].length != n) return false;

            long hash1 = 0, hash2 = 0;
            for (int j = 0; j < n; j++) {
                hash1 = 31 * hash1 + arr1[i][j];
                hash2 = 31 * hash2 + arr2[i][j];
            }

            if (hash1 != hash2) return false;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (arr1[i][j] != arr2[i][j]) return false;
            }
        }

        return true;
    }
}


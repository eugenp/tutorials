package comparing2Darrays;

import java.util.Arrays;

public class OptimizedApproach {

    public static boolean areArraysEqual(int[][] arr1, int[][] arr2) {

        if (arr1 == arr2) return true;
        if (arr1 == null || arr2 == null) return false;
        if (arr1.length != arr2.length) return false;

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].length != arr2[i].length) {
                return false;
            }
        }

        long hash1 = computeHash(arr1);
        long hash2 = computeHash(arr2);

        return hash1 == hash2 && deepCompare(arr1, arr2);
    }

    private static long computeHash(int[][] arr) {
        long hash = 1;
        for (int[] row : arr) {
            for (int value : row) {
                hash = 31 * hash + value;
            }
        }
        return hash;
    }

    private static boolean deepCompare(int[][] arr1, int[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) {
                return false;
            }
        }
        return true;
    }
}


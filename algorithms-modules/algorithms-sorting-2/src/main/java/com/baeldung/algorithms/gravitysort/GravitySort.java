package com.baeldung.algorithms.gravitysort;

public class GravitySort {

    public static int findMax(int[] A) {
        int max = A[0];
        for(int i = 1; i< A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }
        return max;
    }

    public static void setUpAbacus(int[] A, int m) {
        int[][] abacus = new int[A.length][m];
        for (int i = 0; i < abacus.length; i++) {
            int number = A[i];
            for (int j = 0; j < abacus[0].length; j++) {
                if (j < number) {
                    abacus[A.length - 1 - i][j] = 1;
                } else {
                    abacus[i][j] = 0;
                }
            }
        }
    }

    public static void dropBeads(int[][] abacus, int[] A, int m) {
        for(int i = 1; i < A.length; i++) {
            for (int j = m - 1; j >= 0; j--) {
                int x = i;
                if (abacus[x][j] == 1) {
                    while (x > 0 && abacus[x - 1][j] != 1) {
                        int temp = abacus[x - 1][j];
                        abacus[x - 1][j] = abacus[x][j];
                        abacus[x][j] = temp;
                        x--;
                    }
                }
            }
        }
    }
}

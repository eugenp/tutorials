package com.baeldung.algorithms.gravitysort;

public class GravitySort {

    public static int findMax(int[] A) {
        int max = A[0];
        for (int i = 1; i< A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }
        return max;
    }

    public static boolean[][] setupAbacus(int[] A, int m) {
        boolean[][] abacus = new boolean[A.length][m];
        for (int i = 0; i < abacus.length; i++) {
            int number = A[i];
            for (int j = 0; j < abacus[0].length && j < number; j++) {
                abacus[A.length - 1 - i][j] = true;
            }
        }
        return abacus;
    }

    public static void dropBeads(boolean[][] abacus, int[] A, int m) {
        for (int i = 1; i < A.length; i++) {
            for (int j = m - 1; j >= 0; j--) {
                if (abacus[i][j] == true) {
                    int x = i;
                    while (x > 0 && abacus[x - 1][j] == false) {
                        boolean temp = abacus[x - 1][j];
                        abacus[x - 1][j] = abacus[x][j];
                        abacus[x][j] = temp;
                        x--;
                    }
                }
            }
        }
    }

    public static void transformToList(boolean[][] abacus, int[] A) {
        int index = 0;
        for (int i = abacus.length - 1; i >= 0; i--) {
            int beads = 0;
            for (int j = 0; j < abacus[0].length && abacus[i][j] == true; j++) {
                beads++;
            }
            A[index++] = beads;
        }
    }

    public static void sort(int[] A) {
        int m = findMax(A);
        boolean[][] abacus = setupAbacus(A, m);
        dropBeads(abacus, A, m);
        // transform abacus into sorted list
        transformToList(abacus, A);
    }
}

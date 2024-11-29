package com.baeldung.algorithms.inoutsort;

public class InOutSort {

    public static int[] reverseInPlace(int A[]) {
        int n = A.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = A[i];
            A[i] = A[n - 1 - i];
            A[n - 1 - i] = temp;
        }

        return A;
    }

    public static int[] reverseOutOfPlace(int A[]) {
        int n = A.length;
        int[] B = new int[n];
        for (int i = 0; i < n; i++) {
            B[n - i - 1] = A[i];
        }

        return B;
    }
}

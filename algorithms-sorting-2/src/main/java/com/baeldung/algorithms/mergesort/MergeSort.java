package com.baeldung.algorithms.mergesort;

public class MergeSort {

    public static void mergeSort(int A[], int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    public static void merge(int[] A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) {
            L[i] = A[p + i];
        }
        for (int i = 0; i < n2; i++) {
            R[i] = A[q + i + 1];
        }
        int i = 0, j = 0;
        int k = p;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            A[k] = L[i];
            i++;
        }

        while (j < n2) {
            A[k] = R[j];
            j++;
            k++;
        }
    }

}

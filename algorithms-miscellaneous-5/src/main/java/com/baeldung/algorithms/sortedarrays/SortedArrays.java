package com.baeldung.algorithms.sortedarrays;

public class SortedArrays {

    public static int[] merge(int[] first, int[] second) {

        int m = first.length;
        int n = second.length;

        int i, j, k;
        i = j = k = 0;

        int[] result = new int[m + n];

        while (i < m && j < n) {

            if (first[i] < second[j]) {
                result[k++] = first[i++];
            } else {
                result[k++] = second[j++];
            }
        }

        while (i < m) {
            result[k++] = first[i++];
        }

        while (j < n) {
            result[k++] = second[j++];
        }

        return result;
    }
}

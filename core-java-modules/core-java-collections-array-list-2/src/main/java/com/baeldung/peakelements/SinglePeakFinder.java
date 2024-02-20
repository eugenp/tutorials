package com.baeldung.peakelements;

public class SinglePeakFinder {

    public static int findSinglePeak(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                return arr[i];
            }
        }

        if (arr[0] > arr[1]) {
            return arr[0];
        }

        if (arr[n - 1] > arr[n - 2]) {
            return arr[n - 1];
        }

        return -1;
    }
}


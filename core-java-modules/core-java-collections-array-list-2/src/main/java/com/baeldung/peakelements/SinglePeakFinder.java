package com.baeldung.peakelements;

import java.util.OptionalInt;

public class SinglePeakFinder {
    public static OptionalInt findSinglePeak(int[] arr) {
        int n = arr.length;

        if (n < 2) {
            return n == 0 ? OptionalInt.empty() : OptionalInt.of(arr[0]);
        }

        if (arr[0] >= arr[1]) {
            return OptionalInt.of(arr[0]);
        }

        for (int i = 1; i < n - 1; i++) {
            if (arr[i] >= arr[i - 1] && arr[i] >= arr[i + 1]) {
                return OptionalInt.of(arr[i]);
            }
        }

        if (arr[n - 1] >= arr[n - 2]) {
            return OptionalInt.of(arr[n - 1]);
        }

        return OptionalInt.empty();
    }
}


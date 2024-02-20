package com.baeldung.peakelements;

import java.util.ArrayList;
import java.util.List;

public class MultiplePeakFinder {

    public static List<Integer> findPeaks(int[] arr) {

        List<Integer> peaks = new ArrayList<>();

        if (arr == null || arr.length == 0) {
            return peaks;
        }
        findPeakElement(arr, 0, arr.length - 1, peaks);
        return peaks;
    }

    private static void findPeakElement(int[] arr, int low, int high, List<Integer> peaks) {

        if (low > high) {
            return;
        }

        int mid = low + (high - low) / 2;

        if ((mid == 0 || arr[mid] >= arr[mid - 1]) && (mid == arr.length - 1 || arr[mid] >= arr[mid + 1])) {
            peaks.add(arr[mid]);
        }

        if (mid > 0) {
            findPeakElement(arr, low, mid - 1, peaks);
        }

        if (mid < arr.length - 1) {
            findPeakElement(arr, mid + 1, high, peaks);
        }

    }
}

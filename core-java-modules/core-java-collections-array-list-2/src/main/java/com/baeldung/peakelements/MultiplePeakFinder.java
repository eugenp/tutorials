package com.baeldung.peakelements;

import java.util.ArrayList;
import java.util.List;

public class MultiplePeakFinder {

    public static List<Integer> findPeaks(int[] arr) {

        List<Integer> peaks = new ArrayList<>();

        if (arr == null || arr.length == 0) {
            return peaks;
        }
        findPeakElements(arr, 0, arr.length - 1, peaks, arr.length);
        return peaks;
    }

    private static void findPeakElements(int[] arr, int low, int high, List<Integer> peaks, int length) {

        if (low > high) {
            return;
        }

        int mid = low + (high - low) / 2;

        boolean isPeak = (mid == 0 || arr[mid] > arr[mid - 1]) && (mid == length - 1 || arr[mid] > arr[mid + 1]);
        boolean isFirstInSequence = mid > 0 && arr[mid] == arr[mid - 1] && arr[mid] > arr[mid + 1];

        if (isPeak || isFirstInSequence) {

            if (!peaks.contains(arr[mid])) {
                peaks.add(arr[mid]);
            }
        }

        findPeakElements(arr, low, mid - 1, peaks, length);
        findPeakElements(arr, mid + 1, high, peaks, length);
    }
}

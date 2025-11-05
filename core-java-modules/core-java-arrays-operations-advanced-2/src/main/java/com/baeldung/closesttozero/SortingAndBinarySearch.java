package com.baeldung.closesttozero;

import java.util.Arrays;

public class SortingAndBinarySearch {

    public static int findClosestToZero(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or Empty");
        }

        Arrays.sort(arr);
        int closestNumber = arr[0];
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (Math.abs(arr[mid]) < Math.abs(closestNumber)) {
                closestNumber = arr[mid];
            }

            if (arr[mid] < 0) {
                left = mid + 1;
            } else if (arr[mid] > 0) {
                right = mid - 1;
            } else {
                return arr[mid];
            }
        }

        return closestNumber;
    }
}

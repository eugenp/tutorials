package com.baeldung.closesttozero;

public class BruteForce {

    public static int findClosestToZero(int[] arr) throws IllegalAccessException {
        if (arr == null || arr.length == 0) {
            throw new IllegalAccessException("Array must not be null or Empty");
        }

        int closest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if ((Math.abs(arr[i]) < Math.abs(closest)) || ((Math.abs(arr[i]) == Math.abs(closest)) && (arr[i] > closest))) {
                closest = arr[i];
            }
        }
        return closest;
    }
}

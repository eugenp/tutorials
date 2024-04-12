package com.baeldung.algorithms.rotatearray;

/**
 *  To speed up the rotation, we narrow k rotations to the remainder of k divided by the array length, or k module the array length.
 *  Therefore, a large rotation number will be translated into the relative smallest rotation.
 *  All solutions replace the original array, although they might use an extra array to compute the rotation.
 */
public class RotateArray {

    private RotateArray() {
        throw new IllegalStateException("Rotate array algorithm utility methods class");
    }

    /**
     *
     * @param arr array to apply rotation to
     * @param k number of rotations
     */
    public static void bruteForce(int[] arr, int k) {
        checkInvalidInput(arr, k);

        k %= arr.length;
        int temp;
        int previous;
        for (int i = 0; i < k; i++) {
            previous = arr[arr.length - 1];
            for (int j = 0; j < arr.length; j++) {
                temp = arr[j];
                arr[j] = previous;
                previous = temp;
            }
        }
    }

    /**
     *
     * @param arr array to apply rotation to
     * @param k number of rotations
     */
    public static void withExtraArray(int[] arr, int k) {
        checkInvalidInput(arr, k);

        int[] extraArray = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            extraArray[(i + k) % arr.length] = arr[i];
        }
        System.arraycopy(extraArray, 0, arr, 0, arr.length);
    }

    /**
     *
     * @param arr array to apply rotation to
     * @param k number of rotations
     */
    public static void cyclicReplacement(int[] arr, int k) {
        checkInvalidInput(arr, k);

        k = k % arr.length;
        int count = 0;
        for (int start = 0; count < arr.length; start++) {
            int current = start;
            int prev = arr[start];
            do {
                int next = (current + k) % arr.length;
                int temp = arr[next];
                arr[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }

    /**
     *
     * @param arr array to apply rotation to
     * @param k number of rotations
     */
    public static void reverse(int[] arr, int k) {
        checkInvalidInput(arr, k);

        k %= arr.length;
        reverse(arr, 0, arr.length - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    private static void checkInvalidInput(int[] arr, int rotation) {
        if (rotation < 1 || arr == null)
            throw new IllegalArgumentException("Rotation must be greater than zero or array must be not null");
    }
}

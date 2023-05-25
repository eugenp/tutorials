package com.baeldung.algorithms.insertionsort;

public class InsertionSort {

    public static void insertionSortImperative(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;
            while (j >= 0 && input[j] > key) {
                input[j + 1] = input[j];
                j = j - 1;
            }
            input[j + 1] = key;
        }
    }

    public static void insertionSortRecursive(int[] input) {
        insertionSortRecursive(input, input.length);
    }

    private static void insertionSortRecursive(int[] input, int i) {
        // base case
        if (i <= 1) {
            return;
        }

        // sort the first i - 1 elements of the array
        insertionSortRecursive(input, i - 1);

        // then find the correct position of the element at position i
        int key = input[i - 1];
        int j = i - 2;
        // shifting the elements from their position by 1
        while (j >= 0 && input[j] > key) {
            input[j + 1] = input[j];
            j = j - 1;
        }
        // inserting the key at the appropriate position
        input[j + 1] = key;
    }
}

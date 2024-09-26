package com.baeldung.sorted.operations;

import java.util.Arrays;

public class InsertElementInSortedArray {

    public static int[] insertInSortedArray(int[] arr, int numToInsert) {
        int index = Arrays.binarySearch(arr, numToInsert);

        if (index < 0) {
            index = -(index + 1);
        }

        int[] newArray = new int[arr.length + 1];

        System.arraycopy(arr, 0, newArray, 0, index);

        newArray[index] = numToInsert;

        System.arraycopy(arr, index, newArray, index + 1, arr.length - index);

        return newArray;
    }
}

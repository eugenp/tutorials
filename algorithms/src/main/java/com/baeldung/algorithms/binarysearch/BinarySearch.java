package com.baeldung.algorithms.binarysearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinarySearch {

    public int runBinarySearchIteratively(int[] sortedArray, int key, int low, int high) {

        int index = Integer.MAX_VALUE;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (sortedArray[mid] < key) {
                low = mid + 1;
            } else if (sortedArray[mid] > key) {
                high = mid - 1;
            } else if (sortedArray[mid] == key) {
                index = mid;
                break;
            }
        }
        return index;
    }

    public int runBinarySearchRecursively(int[] sortedArray, int key, int low, int high) {

        int middle = (low + high) / 2;
        if (high < low) {
            return -1;
        }

        if (key == sortedArray[middle]) {
          return middle;
        } else if (key < sortedArray[middle]) {
          return runBinarySearchRecursively(sortedArray, key, low, middle - 1);
        } else {
          return runBinarySearchRecursively(sortedArray, key, middle + 1, high);
        }
    }

    public int runBinarySearchUsingJavaArrays(int[] sortedArray, Integer key) {
        int index = Arrays.binarySearch(sortedArray, key);
        return index;
    }

    public int runBinarySearchUsingJavaCollections(List<Integer> sortedList, Integer key) {
        int index = Collections.binarySearch(sortedList, key);
        return index;
    }

}

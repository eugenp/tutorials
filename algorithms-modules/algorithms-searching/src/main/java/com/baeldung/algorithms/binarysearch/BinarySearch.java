package com.baeldung.algorithms.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinarySearch {

    public int runBinarySearchIteratively(int[] sortedArray, int key, int low, int high) {

        int index = Integer.MAX_VALUE;

        while (low <= high) {

            int mid = low + ((high - low) / 2);

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

        int middle = low + ((high - low) / 2);
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

    public List<Integer> runBinarySearchOnSortedArraysWithDuplicates(int[] sortedArray, Integer key) {
        int result = startIndexSearch(sortedArray, key);
        List<Integer> results = new ArrayList<>();
        if (result != -1) {
            results.add(result);
        }

        result = endIndexSearch(sortedArray, key);
        if (result != -1) {
            results.add(result);
        }

        return results;
    }

    private int endIndexSearch(int[] sortedArray, int target) {
        int left = 0;
        int right = sortedArray.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sortedArray[mid] == target) {
                result = mid;
                left = mid + 1;
            } else if (sortedArray[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    private int startIndexSearch(int[] sortedArray, int target) {
        int left = 0;
        int right = sortedArray.length - 1;
        int result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (sortedArray[mid] == target) {
                result = mid;
                right = mid - 1;
            } else if (sortedArray[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

}

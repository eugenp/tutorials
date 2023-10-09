package com.baeldung.mergeandremoveduplicate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class MergeArraysAndRemoveDuplicate {

    public static int[] removeDuplicateOnSortedArray(int[] arr) {
        // Initialize a new array to store unique elements
        int[] uniqueArray = new int[arr.length];
        uniqueArray[0] = arr[0];
        int uniqueCount = 1;

        // Iterate through the sorted array to remove duplicates
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) {
                uniqueArray[uniqueCount] = arr[i];
                uniqueCount++;
            }
        }
        int[] truncatedArray = new int[uniqueCount];
        System.arraycopy(uniqueArray, 0, truncatedArray, 0, uniqueCount);
        return truncatedArray;
    }

    public static int[] mergeAndRemoveDuplicatesUsingStream(int[] arr1, int[] arr2) {
        Stream<Integer> s1 = Arrays.stream(arr1).boxed();
        Stream<Integer> s2 = Arrays.stream(arr2).boxed();
        return Stream.concat(s1, s2)
                .distinct()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static int[] mergeAndRemoveDuplicatesUsingSet(int[] arr1, int[] arr2) {
        int[] mergedArr = mergeArrays(arr1, arr2);
        Set<Integer> uniqueInts = new HashSet<>();

        for (int el : mergedArr) {
            uniqueInts.add(el);
        }

        return getArrayFromSet(uniqueInts);
    }

    private static int[] getArrayFromSet(Set<Integer> set) {
        int[] mergedArrWithoutDuplicated = new int[set.size()];
        int i = 0;
        for (Integer el: set) {
            mergedArrWithoutDuplicated[i] = el;
            i++;
        }
        return mergedArrWithoutDuplicated;
    }

    public static int[] mergeAndRemoveDuplicates(int[] arr1, int[] arr2) {
        return removeDuplicate(mergeArrays(arr1, arr2));
    }

    public static int[] mergeAndRemoveDuplicatesOnSortedArray(int[] arr1, int[] arr2) {
        return removeDuplicateOnSortedArray(mergeArrays(arr1, arr2));
    }

    private static int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] mergedArrays = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, mergedArrays, 0, arr1.length);
        System.arraycopy(arr2, 0, mergedArrays, arr1.length, arr2.length);

        return mergedArrays;
    }
    private static int[] removeDuplicate(int[] arr) {
        int[] withoutDuplicates = new int[arr.length];
        int i = 0;

        for(int element : arr) {
            if(!isElementPresent(withoutDuplicates, element)) {
                withoutDuplicates[i] = element;
                i++;
            }
        }
        int[] truncatedArray = new int[i];
        System.arraycopy(withoutDuplicates, 0, truncatedArray, 0, i);
        return truncatedArray;
    }

    private static boolean isElementPresent(int[] arr, int element) {
        for(int el : arr) {
            if(el == element) {
                return true;
            }
        }
        return false;
    }
}

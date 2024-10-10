package com.baeldung.algorithms.firstduplicate;

import java.util.HashSet;

public class FirstDuplicate {

    public int firstDuplicateBruteForce(int[] arr) {
        int minIndex = arr.length;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    minIndex = Math.min(minIndex, j);
                    break;
                }
            }
        }
        return minIndex == arr.length ? -1 : minIndex;
    }

    public int firstDuplicateHashSet(int[] arr) {
        HashSet<Integer> firstDuplicateSet = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (firstDuplicateSet.contains(arr[i])) {
                return i;
            }
            firstDuplicateSet.add(arr[i]);
        }
        return -1;
    }

    public int firstDuplicateArrayIndexing(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int val = Math.abs(arr[i]) - 1;
            if (arr[val] < 0) {
                return i;
            }
            arr[val] = -arr[val];
        }
        return -1;
    }
}

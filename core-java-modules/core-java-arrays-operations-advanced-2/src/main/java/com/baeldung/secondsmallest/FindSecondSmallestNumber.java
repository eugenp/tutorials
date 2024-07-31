package com.baeldung.secondsmallest;

import java.util.Arrays;
import java.util.PriorityQueue;

public class FindSecondSmallestNumber {

    public static int usingArraySort(int[] arr) {
        Arrays.sort(arr);
        int smallest = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != smallest) {
                return arr[i];
            }
        }
        return -1;
    }

    public static int usingSinglePassThrough(int[] arr) {
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int num : arr) {
            if (num < smallest) {
                secondSmallest = smallest;
                smallest = num;
            } else if (num < secondSmallest && num != smallest) {
                secondSmallest = num;
            }
        }

        if (secondSmallest == Integer.MAX_VALUE) {
            return -1;
        } else {
            return secondSmallest;
        }
    }

    public static int usingMinHeap(int[] arr) {
        if (arr.length < 2) {
            return -1;
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : arr) {
            if (minHeap.isEmpty() || num != minHeap.peek()) {
                minHeap.offer(num);
            }
        }
        // Check if the heap less than 2 (all elements were the same)
        if (minHeap.size() < 2) {
            return -1;
        }

        minHeap.poll(); // Remove the smallest element
        return minHeap.peek();  // Second smallest element is at the root (peek)
    }

}

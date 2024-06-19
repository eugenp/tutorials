package com.baeldung.closesttozero;

import java.util.PriorityQueue;

public class PriorityQueueToZero {
    public static int findClosestToZeroWithPriorityQueue(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Math.abs(b) - Math.abs(a));

        for (int num : arr) {
            pq.offer(num);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        return pq.peek();
    }
}

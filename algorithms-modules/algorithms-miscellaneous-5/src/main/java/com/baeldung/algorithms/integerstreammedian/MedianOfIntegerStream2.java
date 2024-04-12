package com.baeldung.algorithms.integerstreammedian;

import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Comparator.reverseOrder;

public class MedianOfIntegerStream2 {

    private Queue<Integer> minHeap, maxHeap;

    MedianOfIntegerStream2() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(reverseOrder());
    }

    void add(int num) {
        if (minHeap.size() == maxHeap.size()) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll());
        } else {
            minHeap.offer(num);
            maxHeap.offer(minHeap.poll());
        }
    }

    double getMedian() {
        int median;
        if (minHeap.size() > maxHeap.size()) {
            median = minHeap.peek();
        } else {
            median = (minHeap.peek() + maxHeap.peek()) / 2;
        }
        return median;
    }
}

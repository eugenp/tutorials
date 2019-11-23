package com.baeldung.algorithms.datastreammedian;

import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Comparator.reverseOrder;

public class DataStreamMedian {

    private Queue<Integer> minHeap, maxHeap;

    DataStreamMedian() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>(reverseOrder());
    }

    void add(int num) {
        minHeap.offer(num);
        maxHeap.offer(minHeap.poll());

        if (minHeap.size() < maxHeap.size()) {
            minHeap.offer(maxHeap.poll());
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

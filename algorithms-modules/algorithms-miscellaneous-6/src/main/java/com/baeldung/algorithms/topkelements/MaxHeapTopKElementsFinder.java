package com.baeldung.algorithms.topkelements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MaxHeapTopKElementsFinder implements TopKElementsFinder<Integer> {

    public List<Integer> findTopK(List<Integer> input, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();

        input.forEach(number -> {
            maxHeap.add(number);

            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        });

        List<Integer> topKList = new ArrayList<>(maxHeap);
        Collections.reverse(topKList);

        return topKList;
    }
}

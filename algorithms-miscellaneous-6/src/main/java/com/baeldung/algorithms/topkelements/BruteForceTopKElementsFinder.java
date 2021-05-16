package com.baeldung.algorithms.topkelements;

import java.util.ArrayList;
import java.util.List;

public class BruteForceTopKElementsFinder implements TopKElementsFinder<Integer> {

    public List<Integer> findTopK(List<Integer> input, int k) {
        List<Integer> array = new ArrayList<>(input);
        List<Integer> topKList = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int maxIndex = 0;

            for (int j = 1; j < array.size(); j++) {
                if (array.get(j) > array.get(maxIndex)) {
                    maxIndex = j;
                }
            }

            topKList.add(array.remove(maxIndex));
        }

        return topKList;
    }
}

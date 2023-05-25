package com.baeldung.algorithms.topkelements;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TreeSetTopKElementsFinder implements TopKElementsFinder<Integer> {

    public List<Integer> findTopK(List<Integer> input, int k) {
        Set<Integer> sortedSet = new TreeSet<>(Comparator.reverseOrder());
        sortedSet.addAll(input);

        return sortedSet.stream().limit(k).collect(Collectors.toList());
    }
}

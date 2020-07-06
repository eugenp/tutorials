package com.baeldung.algorithms.topnelements;

import java.util.List;

public interface TopKElementsFinder<T extends Comparable<T>> {
    List<T> findTopK(List<T> input, int k);
}

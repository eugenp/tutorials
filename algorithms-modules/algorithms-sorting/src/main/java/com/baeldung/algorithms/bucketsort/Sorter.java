package com.baeldung.algorithms.bucketsort;

import java.util.List;

public interface Sorter<T> {

    List<T> sort(List<T> arrayToSort);
}

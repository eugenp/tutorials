package com.baeldung.array;

import java.util.Comparator;

public class SortedArrayChecker {

    boolean isSorted(int[] array, int length) {
        if (array == null || length < 2)
            return true;

        if (array[length - 2] > array[length - 1])
            return false;

        return isSorted(array, length - 1);
    }

    boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }

        return true;
    }

    boolean isSorted(Comparable[] array, int length) {
        if (array == null || length < 2)
            return true;

        if (array[length - 2].compareTo(array[length - 1]) > 0)
            return false;

        return isSorted(array, length - 1);
    }

    boolean isSorted(Comparable[] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i].compareTo(array[i + 1]) > 0)
                return false;
        }

        return true;
    }

    boolean isSorted(Object[] array, Comparator comparator) {
        for (int i = 0; i < array.length - 1; ++i) {
            if (comparator.compare(array[i], (array[i + 1])) > 0)
                return false;
        }

        return true;
    }

    boolean isSorted(Object[] array, Comparator comparator, int length) {
        if (array == null || length < 2)
            return true;

        if (comparator.compare(array[length - 2], array[length - 1]) > 0)
            return false;

        return isSorted(array, comparator, length - 1);
    }
}

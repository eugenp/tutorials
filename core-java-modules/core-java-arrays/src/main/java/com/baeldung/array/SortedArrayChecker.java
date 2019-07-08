package com.baeldung.array;

import com.baeldung.arraycopy.model.Employee;

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

    boolean isSorted(String[] array, int length) {
        if (array == null || length < 2)
            return true;

        if (array[length - 2].compareTo(array[length - 1]) > 0)
            return false;

        return isSorted(array, length - 1);
    }

boolean isSorted(String[] array) {
    for (int i = 0; i < array.length - 1; ++i) {
        if (array[i].compareTo(array[i + 1]) > 0)
            return false;
    }

    return true;
}

    boolean isSortedByName(Employee[] array) {
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i].getName().compareTo(array[i + 1].getName()) > 0)
                return false;
        }

        return true;
    }

boolean isSortedByAge(Employee[] array) {
    for (int i = 0; i < array.length - 1; ++i) {
        if (array[i].getAge() > (array[i + 1].getAge()))
            return false;
    }

    return true;
}

    boolean isSortedByAge(Employee[] array, int length) {
        if (array == null || length < 2)
            return true;

        if (array[length - 2].getAge() > array[length - 1].getAge())
            return false;

        return isSortedByAge(array, length - 1);
    }
}

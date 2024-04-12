package com.baeldung.list.reverse;

import java.util.List;

public class ReverseArrayList {
    private ReverseArrayList() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static <T> void reverseWithRecursion(List<T> list) {
        if (list.size() > 1) {
            T value = list.remove(0);
            reverseWithRecursion(list);
            list.add(value);
        }
    }

    public static <T> void reverseWithLoop(List<T> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++) {
            list.add(i, list.remove(j));
        }
    }
}

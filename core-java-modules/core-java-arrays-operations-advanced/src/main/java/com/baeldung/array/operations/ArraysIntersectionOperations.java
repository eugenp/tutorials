package com.baeldung.array.operations;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Stream;

public class ArraysIntersectionOperations {

    public static Integer[] intersectionSimple(final Integer[] a, final Integer[] b) {
        return Stream.of(a)
            .filter(Arrays.asList(b)::contains)
            .toArray(Integer[]::new);
    }

    public static Integer[] intersectionSet(final Integer[] a, final Integer[] b) {
        return Stream.of(a)
            .filter(Arrays.asList(b)::contains)
            .distinct()
            .toArray(Integer[]::new);
    }

    public static Integer[] intersectionMultiSet(final Integer[] a, final Integer[] b) {
        return Stream.of(a)
            .filter(new LinkedList<>(Arrays.asList(b))::remove)
            .toArray(Integer[]::new);
    }
}

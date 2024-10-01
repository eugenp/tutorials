package com.baeldung.algorithms.countoccurence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CountOccurrence {

    public static int[] countOccurrencesWithCounter(int[] elements, int n) {
        int[] counter = new int[n];

        for (int element : elements) {
            counter[element]++;
        }

        return counter;
    }

    public static <T> Map<T, Integer> countOccurrencesWithMap(T[] elements) {

        Map<T, Integer> counter = new HashMap<>();

        for (T element : elements) {
            counter.merge(element, 1, Integer::sum);
        }

        return counter;
    }

    public static <T> Map<T, Long> countOccurrencesWithStream(T[] elements) {

        return Arrays.stream(elements)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}

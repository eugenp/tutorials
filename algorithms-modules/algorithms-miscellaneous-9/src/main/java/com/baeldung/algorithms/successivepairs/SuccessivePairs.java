package com.baeldung.algorithms.successivepairs;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SuccessivePairs {

    public static <T> List<SimpleEntry<T, T>> collectSuccessivePairs(Stream<T> stream) {
        List<T> list = stream.collect(Collectors.toList());
        return IntStream.range(0, list.size() - 1)
            .mapToObj(i -> new SimpleEntry<>(list.get(i), list.get(i + 1)))
            .collect(Collectors.toList());
    }

    public static <T> Stream<List<T>> pairwise(Stream<T> stream) {
        List<T> list = stream.collect(Collectors.toList());
        List<List<T>> pairs = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            pairs.add(Arrays.asList(list.get(i), list.get(i + 1)));
        }
        return pairs.stream();
    }
}

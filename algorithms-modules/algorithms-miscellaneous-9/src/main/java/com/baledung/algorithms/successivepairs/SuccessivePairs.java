package com.baledung.algorithms.successivepairs;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
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

    public static <T> Collector<T, List<SimpleEntry<T, T>>, List<SimpleEntry<T, T>>> toPairList() {
        return Collector.of(
            ArrayList::new, // Supplier
            (list, item) -> { // Accumulator
                if (!list.isEmpty()) {
                    list.add(new SimpleEntry<>(list.get(list.size() - 1).getValue(), item));
                }
                list.add(new SimpleEntry<>(null, item)); // Add placeholder for the next pair
            },
            (left, right) -> { // Combiner
                if (!left.isEmpty() && !right.isEmpty()) {
                    left.add(new SimpleEntry<>(left.get(left.size() - 1).getValue(), right.get(0).getValue()));
                }
                left.addAll(right);
                return left;
            },
            list -> { // Finisher
                List<SimpleEntry<T, T>> pairs = new ArrayList<>();
                for (int i = 1; i < list.size(); i += 2) {
                    pairs.add(list.get(i));
                }
                return pairs;
            }
        );
    }
}

package com.baeldung.stream.sum;

import java.util.List;
import java.util.stream.Collectors;

public class StreamSumCalculatorWithObject {

    public static Integer getSumUsingCustomizedAccumulator(List<Item> items) {
        return items.stream()
            .map(x -> x.getPrice())
            .reduce(0, ArithmeticUtils::add);
    }

    public static Integer getSumUsingJavaAccumulator(List<Item> items) {
        return items.stream()
            .map(x -> x.getPrice())
            .reduce(0, Integer::sum);
    }

    public static Integer getSumUsingReduce(List<Item> items) {
        return items.stream()
            .map(item -> item.getPrice())
            .reduce(0, (a, b) -> a + b);
    }

    public static Integer getSumUsingCollect(List<Item> items) {
        return items.stream()
            .map(x -> x.getPrice())
            .collect(Collectors.summingInt(Integer::intValue));
    }

    public static Integer getSumUsingSum(List<Item> items) {
        return items.stream()
            .mapToInt(x -> x.getPrice())
            .sum();
    }

}

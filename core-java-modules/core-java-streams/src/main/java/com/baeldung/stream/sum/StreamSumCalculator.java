package com.baeldung.stream.sum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamSumCalculator {

    public static Integer getSumUsingCustomizedAccumulator(List<Integer> integers) {
        return integers.stream()
            .reduce(0, ArithmeticUtils::add);

    }

    public static Integer getSumUsingJavaAccumulator(List<Integer> integers) {
        return integers.stream()
            .reduce(0, Integer::sum);

    }

    public static Integer getSumUsingReduce(List<Integer> integers) {
        return integers.stream()
            .reduce(0, (a, b) -> a + b);

    }

    public static Integer getSumUsingCollect(List<Integer> integers) {

        return integers.stream()
            .collect(Collectors.summingInt(Integer::intValue));

    }

    public static Integer getSumUsingSum(List<Integer> integers) {

        return integers.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    public static Integer getSumOfMapValues(Map<Object, Integer> map) {

        return map.values()
            .stream()
            .mapToInt(Integer::valueOf)
            .sum();
    }

    public static Integer getSumIntegersFromString(String str) {

        Integer sum = Arrays.stream(str.split(" "))
            .filter((s) -> s.matches("\\d+"))
            .mapToInt(Integer::valueOf)
            .sum();

        return sum;
    }
}

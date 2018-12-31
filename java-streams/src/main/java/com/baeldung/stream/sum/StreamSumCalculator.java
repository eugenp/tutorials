package com.baeldung.stream.sum;

import java.util.List;
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
}

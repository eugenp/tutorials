package com.baeldung.stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkipLimitComparison {

    public static void main(String[] args) {
        skipExample();
        limitExample();
        limitInfiniteStreamExample();
        getEvenNumbers(10, 10).stream()
            .forEach(System.out::println);
    }

    public static void skipExample() {
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter(i -> i % 2 == 0)
            .skip(2)
            .forEach(i -> System.out.print(i + " "));
    }

    public static void limitExample() {
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter(i -> i % 2 == 0)
            .limit(2)
            .forEach(i -> System.out.print(i + " "));
    }

    public static void limitInfiniteStreamExample() {
        Stream.iterate(0, i -> i + 1)
            .filter(i -> i % 2 == 0)
            .limit(10)
            .forEach(System.out::println);
    }

    private static List<Integer> getEvenNumbers(int offset, int limit) {
        return Stream.iterate(0, i -> i + 1)
            .filter(i -> i % 2 == 0)
            .skip(offset)
            .limit(limit)
            .collect(Collectors.toList());
    }

}

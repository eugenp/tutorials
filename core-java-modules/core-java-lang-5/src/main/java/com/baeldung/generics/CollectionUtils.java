package com.baeldung.generics;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static <T> void print(T item) {
        System.out.println(item);
    }

    public static void swap(List<?> list, int src, int des) {
        swapHelper(list, src, des);
    }

    private static <E> void swapHelper(List<E> list, int src, int des) {
        list.set(src, list.set(des, list.get(src)));
    }

    public static <E> List<E> mergeTypeParameter(List<? extends E> listOne, List<? extends E> listTwo) {
        return Stream.concat(listOne.stream(), listTwo.stream())
            .collect(Collectors.toList());
    }

    public static <E> List<? extends E> mergeWildcard(List<? extends E> listOne, List<? extends E> listTwo) {
        return Stream.concat(listOne.stream(), listTwo.stream())
            .collect(Collectors.toList());
    }

    public static long sum(List<Number> numbers) {
        return numbers.stream()
            .mapToLong(Number::longValue)
            .sum();
    }

    public static <T extends Number> long sumTypeParameter(List<T> numbers) {
        return numbers.stream()
            .mapToLong(Number::longValue)
            .sum();
    }

    public static long sumWildcard(List<? extends Number> numbers) {
        return numbers.stream()
            .mapToLong(Number::longValue)
            .sum();
    }

    public static void addNumber(List<? super Integer> list, Integer number) {
        list.add(number);
    }

}

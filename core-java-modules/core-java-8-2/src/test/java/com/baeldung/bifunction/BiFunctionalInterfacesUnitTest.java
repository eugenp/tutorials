package com.baeldung.bifunction;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BiFunctionalInterfacesUnitTest {
    @Test
    public void mapAStreamWithUnaryFunction() {
        List<String> mapped = Stream.of("hello", "world")
                .map(word -> word + "!")
                .collect(Collectors.toList());

        assertThat(mapped).containsExactly("hello!", "world!");
    }

    @Test
    public void reduceAStreamByPrefix() {
        String result = Stream.of("hello", "world")
                .reduce("", (a, b) -> b + "-" + a);

        assertThat(result).isEqualTo("world-hello-");
    }

    @Test
    public void reduceAStreamByPrefixWithoutTrailingDash() {
        String result = Stream.of("hello", "world")
                .reduce("", (a, b) -> combineWithoutTrailingDash(a, b));

        assertThat(result).isEqualTo("world-hello");
    }

    private String combineWithoutTrailingDash(String a, String b) {
        if (a.isEmpty()) {
            return b;
        }
        return b + "-" + a;
    }

    @Test
    public void reduceAStreamByPrefixWithoutTrailingDashUsingMethodReference() {
        String result = Stream.of("hello", "world")
                .reduce("", this::combineWithoutTrailingDash);

        assertThat(result).isEqualTo("world-hello");
    }

    @Test
    public void combineTwoLists() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        List<String> result = new ArrayList<>();
        for (int i=0; i < list1.size(); i++) {
            result.add(list1.get(i) + list2.get(i));
        }

        assertThat(result).containsExactly("a1", "b2", "c3");
    }

    @Test
    public void combineTwoListsWithGeneralisedFunction() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        List<String> result = listCombiner(list1, list2, (a, b) -> a + b);

        assertThat(result).containsExactly("a1", "b2", "c3");
    }

    private static <T, U, R> List<R> listCombiner(List<T> list1,
                                                  List<U> list2,
                                                  BiFunction<T, U, R> combiner) {
        List<R> result = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            result.add(combiner.apply(list1.get(i), list2.get(i)));
        }
        return result;
    }

    @Test
    public void compareTwoLists() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        // algorithm to determine if the value in list1 > value in list 2
        List<Boolean> result = listCombiner(list1, list2, (a, b) -> a > b);

        assertThat(result).containsExactly(true, true, false);
    }

    @Test
    public void compareTwoListsWithMethodReference() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        // algorithm to determine if the value in list1 > value in list 2
        List<Boolean> result = listCombiner(list1, list2, this::firstIsGreaterThanSecond);

        assertThat(result).containsExactly(true, true, false);
    }

    private boolean firstIsGreaterThanSecond(Double a, Float b) {
        return a > b;
    }

    @Test
    public void compareTwoListsHaveEqualValues() {
        List<Float> list1 = Arrays.asList(0.1f, 0.2f, 4f);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        List<Boolean> result = listCombiner(list1, list2, (a, b) -> a.equals(b));

        assertThat(result).containsExactly(true, true, true);
    }

    @Test
    public void compareTwoListsHaveEqualValuesMethodReference() {
        List<Float> list1 = Arrays.asList(0.1f, 0.2f, 4f);
        List<Float> list2 = Arrays.asList(0.1f, 0.2f, 4f);

        List<Boolean> result = listCombiner(list1, list2, Float::equals);

        assertThat(result).containsExactly(true, true, true);
    }

    @Test
    public void compareTwoListsWithCompareTo() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Double> list2 = Arrays.asList(0.1d, 0.2d, 4d);

        List<Integer> result = listCombiner(list1, list2, Double::compareTo);

        assertThat(result).containsExactly(1, 1, -1);
    }

    /**
     * Allows you to to pass in a lambda or method reference and then
     * get access to the BiFunction it is meant to become
     */
    private static <T, U, R> BiFunction<T, U, R> asBiFunction(BiFunction<T, U, R> function) {
        return function;
    }

    @Test
    public void compareTwoListsWithCompareToAsGreaterThan() {
        List<Double> list1 = Arrays.asList(1.0d, 2.1d, 3.3d);
        List<Double> list2 = Arrays.asList(0.1d, 0.2d, 4d);

        List<Boolean> result = listCombiner(list1, list2,
                asBiFunction(Double::compareTo)
                    .andThen(i -> i > 0));

        assertThat(result).containsExactly(true, true, false);
    }
}

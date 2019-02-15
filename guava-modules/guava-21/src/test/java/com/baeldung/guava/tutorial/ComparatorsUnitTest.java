package com.baeldung.guava.tutorial;

import com.google.common.collect.Comparators;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class ComparatorsUnitTest {

    @Test
    public void isInOrderTest() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 4, 6, 7, 8, 9, 10);

        boolean isInAscendingOrder = Comparators.isInOrder(numbers, new AscendingOrderComparator<Number>());

        Assert.assertTrue(isInAscendingOrder);
    }

    @Test
    public void isInStrictOrderTest() {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 3, 6, 7, 8, 9, 10);

        boolean isInAscendingOrder = Comparators.isInOrder(numbers, new AscendingOrderComparator<Number>());

        Assert.assertFalse(isInAscendingOrder);
    }

    private class AscendingOrderComparator<I extends Number> implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }

        @Override
        public Comparator<Integer> reversed() {
            return null;
        }

        @Override
        public Comparator<Integer> thenComparing(Comparator<? super Integer> other) {
            return null;
        }

        @Override
        public <U> Comparator<Integer> thenComparing(Function<? super Integer, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
            return null;
        }

        @Override
        public <U extends Comparable<? super U>> Comparator<Integer> thenComparing(Function<? super Integer, ? extends U> keyExtractor) {
            return null;
        }

        @Override
        public Comparator<Integer> thenComparingInt(ToIntFunction<? super Integer> keyExtractor) {
            return null;
        }

        @Override
        public Comparator<Integer> thenComparingLong(ToLongFunction<? super Integer> keyExtractor) {
            return null;
        }

        @Override
        public Comparator<Integer> thenComparingDouble(ToDoubleFunction<? super Integer> keyExtractor) {
            return null;
        }
    }
}

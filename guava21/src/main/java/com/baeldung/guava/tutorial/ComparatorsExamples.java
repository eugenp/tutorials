package com.baeldung.guava.tutorial;

import com.google.common.collect.Comparators;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ComparatorsExamples {

    public static void main(String[] args) {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 4, 6, 7, 8, 9, 10);
        boolean isInAscendingOrder = Comparators.isInOrder(integers, new AscedingOrderComparator());
        System.out.println(isInAscendingOrder);

    }

    private static class AscedingOrderComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    }
}

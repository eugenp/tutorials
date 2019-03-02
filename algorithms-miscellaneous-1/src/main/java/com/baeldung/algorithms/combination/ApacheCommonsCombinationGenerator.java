package com.baeldung.algorithms.combination;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class ApacheCommonsCombinationGenerator {

    public static void main(String[] args) {
        Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(5, 3);
        while (iterator.hasNext()) {
            final int[] combination = iterator.next();
            System.out.println(Arrays.toString(combination));
        }
    }
}
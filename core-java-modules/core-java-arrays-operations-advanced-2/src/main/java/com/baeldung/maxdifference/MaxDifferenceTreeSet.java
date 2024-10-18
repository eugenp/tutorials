package com.baeldung.maxdifference;

import java.util.TreeSet;

public class MaxDifferenceTreeSet {

    public static int[] findMaxDifferenceTreeSet(int[] list) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int num : list) {
            set.add(num);
        }

        int minValue = set.first();
        int maxValue = set.last();
        int indexOne = 0;
        int indexTwo = list.length - 1;

        for (int i = 0; i < list.length; i++) {
            if (list[i] == minValue) {
                indexOne = i;
            } else if (list[i] == maxValue) {
                indexTwo = i;
            }
        }

        int maxDifference = maxValue - minValue;
        return new int[] { indexOne, indexTwo, list[indexOne], list[indexTwo], maxDifference };
    }
}
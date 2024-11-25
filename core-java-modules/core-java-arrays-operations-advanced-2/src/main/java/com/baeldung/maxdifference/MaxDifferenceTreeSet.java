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
        int minIndex = 0;
        int maxIndex = list.length - 1;

        for (int i = 0; i < list.length; i++) {
            if (list[i] == minValue) {
                minIndex = i;
            } else if (list[i] == maxValue) {
                maxIndex = i;
            }
        }

        int maxDifference = Math.abs(maxValue - minValue);
        int[] result = new int[] { minIndex, maxIndex, list[minIndex], list[maxIndex], maxDifference };
        return result;
    }
}
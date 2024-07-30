package com.baeldung.algorithms.subarray;

import java.util.HashMap;
import java.util.Map;

public class SubarrayMeansPrefixSums {

    public static int countSubarraysWithMean(int[] inputArray, int mean) {
        int n = inputArray.length;
        long[] prefixSums = new long[n + 1];
        long[] adjustedPrefixes = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefixSums[i + 1] = prefixSums[i] + inputArray[i];
            adjustedPrefixes[i + 1] = prefixSums[i + 1] - (long) mean * (i + 1);
        }

        Map<Long, Integer> count = new HashMap<>();
        int total = 0;
        for (long adjustedPrefix : adjustedPrefixes) {
            total += count.getOrDefault(adjustedPrefix, 0);
            count.put(adjustedPrefix, count.getOrDefault(adjustedPrefix, 0) + 1);
        }
        return total;
    }
}


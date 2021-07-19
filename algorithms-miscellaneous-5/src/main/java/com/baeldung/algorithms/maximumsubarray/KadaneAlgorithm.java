package com.baeldung.algorithms.maximumsubarray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KadaneAlgorithm {

    private Logger logger = LoggerFactory.getLogger(BruteForceAlgorithm.class.getName());

    public int maxSubArraySum(int[] arr) {

        int size = arr.length;
        int start = 0;
        int end = 0;

        int maxSoFar = arr[0], maxEndingHere = arr[0];
        for (int i = 0; i < size; i++) {

            if (arr[i] > maxEndingHere + arr[i]) {
                start = i;
                maxEndingHere = arr[i];
            } else {
                maxEndingHere = maxEndingHere + arr[i];
            }

            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
                end = i;
            }
        }
        logger.info("Found Maximum Subarray between {} and {}", Math.min(start, end), end);
        return maxSoFar;
    }
}

package com.baeldung.algorithms.maximumsubarray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BruteForceAlgorithm {

    private Logger logger = LoggerFactory.getLogger(BruteForceAlgorithm.class.getName());

    public int maxSubArray(int[] arr) {

        int size = arr.length;
        int maximumSubArraySum = Integer.MIN_VALUE;
        int start = 0;
        int end = 0;

        for (int left = 0; left < size; left++) {

            int runningWindowSum = 0;

            for (int right = left; right < size; right++) {
                runningWindowSum += arr[right];

                if (runningWindowSum > maximumSubArraySum) {
                    maximumSubArraySum = runningWindowSum;
                    start = left;
                    end = right;
                }
            }
        }
        logger.info("Found Maximum Subarray between {} and {}", start, end);
        return maximumSubArraySum;
    }

}

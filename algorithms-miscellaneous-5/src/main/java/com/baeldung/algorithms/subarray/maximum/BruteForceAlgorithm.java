package com.baeldung.algorithms.subarray.maximum;

public class BruteForceAlgorithm {

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
        System.out.println("Found Maximum Subarray between " + start + " and " + end);
        return maximumSubArraySum;
    }

}

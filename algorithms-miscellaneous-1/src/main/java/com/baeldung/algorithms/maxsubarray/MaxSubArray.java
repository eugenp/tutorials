package com.baeldung.algorithms.maxsubarray;

public class MaxSubArray {

    public static int findMaxSubArraySum(int A[], int p, int r) {
        if (p == r) {
            return A[p];
        }
        int q = (p + r) / 2;
        return Math.max(Math.max(findMaxSubArraySum(A, p, q), findMaxSubArraySum(A, q + 1, r)), findMaxCrossingSum(A, p, q, r));
    }

    static int findMaxCrossingSum(int arr[], int p, int q, int r) {
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;
        for (int i = q; i >= p; i--) {
            sum = sum + arr[i];
            if (sum > leftSum)
                leftSum = sum;
        }

        sum = 0;
        int rightSum = Integer.MIN_VALUE;
        for (int i = q + 1; i <= r; i++) {
            sum = sum + arr[i];
            if (sum > rightSum)
                rightSum = sum;
        }
        sum = leftSum + rightSum;
        return Math.max(sum, Math.max(leftSum, rightSum));
    }
}

package com.baeldung.math.rodcutting;

import java.util.Arrays;

public class RodCuttingProblem {
    static int maxRevenueG;
    public static int usingRecursion(int[] prices, int n) {
        if (n <= 0) {
            return 0;
        }
        int maxRevenue = Integer.MIN_VALUE;

        for (int i = 1; i <= n; i++) {
            maxRevenue = Math.max(maxRevenue, prices[i - 1] + usingRecursion(prices, n - i));
        }
        return maxRevenue;
    }

    public static int usingMemoizedRecursion(int[] prices, int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);

        return memoizedHelper(prices, n, memo);
    }

    private static int memoizedHelper(int[] prices, int n, int[] memo) {
        if (n <= 0) {
            return 0;
        }

        if (memo[n] != -1) {
            return memo[n];
        }

        int maxRevenue = Integer.MIN_VALUE;

        for (int i = 1; i <= n; i++) {
            maxRevenue = Math.max(maxRevenue, prices[i - 1] + memoizedHelper(prices, n - i, memo));
        }

        memo[n] = maxRevenue;
        return maxRevenue;
    }

    public static int usingDynamicProgramming(int[] prices, int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int maxRevenue = Integer.MIN_VALUE;

            for (int j = 1; j <= i; j++) {
                maxRevenue = Math.max(maxRevenue, prices[j - 1] + dp[i - j]);
            }
            dp[i] = maxRevenue;
        }
        return dp[n];
    }

    public static int usingUnboundedKnapsack(int[] prices, int n) {
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < prices.length; j++) {
                if (j + 1 <= i) {
                    dp[i] = Math.max(dp[i], dp[i - (j + 1)] + prices[j]);
                }
            }
        }

        return dp[n];
    }

}

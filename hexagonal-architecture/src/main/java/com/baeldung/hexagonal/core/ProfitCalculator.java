package com.baeldung.hexagonal.core;

/**
 * User: raychenon
 * Date: 14/7/2020/
 * No framework dependencies
 */
public class ProfitCalculator {

    /**
     * Find the best time to buy and sell stock
     * find the maximum profit by completing at most one transaction
     * (i.e., buy one and sell one share of the stock)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        int maxProfit = 0;

        int currentPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < currentPrice) {
                currentPrice = prices[i];
            } else {
                maxProfit = Math.max(maxProfit, prices[i] - currentPrice);
            }

        }
        return maxProfit;
    }
}

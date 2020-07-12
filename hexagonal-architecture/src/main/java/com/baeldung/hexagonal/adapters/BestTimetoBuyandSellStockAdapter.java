package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.BestTimetoBuyandSellStockPort;

/**
 * User: raychenon
 * Date: 12/7/2020
 *
 * No framework dependencies
 */
public class BestTimetoBuyandSellStockAdapter implements BestTimetoBuyandSellStockPort {

    @Override
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

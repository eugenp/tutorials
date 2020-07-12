package com.baeldung.hexagonal.ports;

/**
 * User: raychenon
 * Date: 12/7/2020
 */
public interface BestTimetoBuyandSellStockPort {
    /**
     * find the maximum profit by completing at most one transaction
     * (i.e., buy one and sell one share of the stock)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices);
}

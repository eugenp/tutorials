package com.baeldung.hexagonal.adapters;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.baeldung.hexagonal.adapters.BestTimetoBuyandSellStockAdapter;

/**
 * User: raychenon
 * Date: 13/7/2020
 */
public class BestTimetoBuyandSellStockAdapterTest {


    private static BestTimetoBuyandSellStockAdapter bestTimetoBuyandSellStockAdapter;

    @BeforeClass
    public static void setUp() {
        bestTimetoBuyandSellStockAdapter = new com.baeldung.hexagonal.adapters.BestTimetoBuyandSellStockAdapter();
    }

    @Test
    public void bestTimeToBuyTest() {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        Assert.assertEquals(5, bestTimetoBuyandSellStockAdapter.maxProfit(prices));
    }

    @Test
    public void decreasingPricesTest() {
        int[] prices = new int[]{7, 6, 4, 3, 1};
        Assert.assertEquals(0, bestTimetoBuyandSellStockAdapter.maxProfit(prices));
    }

}

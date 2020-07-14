package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.ProfitCalculator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * User: raychenon
 * Date: 13/7/2020
 */
public class ProfitCalculatorTest {


    private static ProfitCalculator profitCalculator;

    @BeforeClass
    public static void setUp() {
        profitCalculator = new ProfitCalculator();
    }

    @Test
    public void bestTimeToBuyTest() {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        Assert.assertEquals(5, profitCalculator.maxProfit(prices));
    }

    @Test
    public void decreasingPricesTest() {
        int[] prices = new int[]{7, 6, 4, 3, 1};
        Assert.assertEquals(0, profitCalculator.maxProfit(prices));
    }

}

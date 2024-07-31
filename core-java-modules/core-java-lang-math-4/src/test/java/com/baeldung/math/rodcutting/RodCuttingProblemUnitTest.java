package com.baeldung.math.rodcutting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RodCuttingProblemUnitTest {
     @Test
     void givenARod_whenUsingRecursion_thenFindMaxRevenue() {
        int[] prices = {1, 5, 8, 9};
        int rodLength = 4;
        int maxRevenue = RodCuttingProblem.usingRecursion(prices, rodLength);
        assertEquals(10, maxRevenue);
    }

    @Test
    void givenARod_whenUsingMemoizedRecursion_thenFindMaxRevenue() {
        int[] prices = {1, 5, 8, 9};
        int rodLength = 4;
        int maxRevenue = RodCuttingProblem.usingMemoizedRecursion(prices, rodLength);
        assertEquals(10, maxRevenue);
    }

    @Test
    void givenARod_whenUsingDynamicProgramming_thenFindMaxRevenue() {
        int[] prices = {1, 5, 8, 9};
        int rodLength = 4;
        int maxRevenue = RodCuttingProblem.usingDynamicProgramming(prices, rodLength);
        assertEquals(10, maxRevenue);
    }

    @Test
    void givenARod_whenUsingGreedy_thenFindMaxRevenue() {
        int[] prices = {1, 5, 8, 9};
        int rodLength = 4;
        int maxRevenue = RodCuttingProblem.usingUnboundedKnapsack(prices, rodLength);
        assertEquals(10, maxRevenue);
    }
}

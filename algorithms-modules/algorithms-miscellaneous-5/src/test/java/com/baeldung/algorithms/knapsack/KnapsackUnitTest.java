package com.baeldung.algorithms.knapsack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class KnapsackUnitTest {

    @Test
    public void givenWeightsandValues_whenCalculateMax_thenOutputCorrectResult() {
        final int[] w = new int[] { 23, 26, 20, 18, 32, 27, 29, 26, 30, 27 };
        final int[] v = new int[] { 505, 352, 458, 220, 354, 414, 498, 545, 473, 543 };
        final int n = 10;
        final int W = 67;
        final Knapsack knapsack = new Knapsack();

        assertEquals(1270, knapsack.knapsackRec(w, v, n, W));
        assertEquals(1270, knapsack.knapsackDP(w, v, n, W));
    }

    @Test
    public void givenZeroItems_whenCalculateMax_thenOutputZero() {
        final int[] w = new int[] {};
        final int[] v = new int[] {};
        final int n = 0;
        final int W = 67;
        final Knapsack knapsack = new Knapsack();

        assertEquals(0, knapsack.knapsackRec(w, v, n, W));
        assertEquals(0, knapsack.knapsackDP(w, v, n, W));
    }

    @Test
    public void givenZeroWeightLimit_whenCalculateMax_thenOutputZero() {
        final int[] w = new int[] { 23, 26, 20, 18, 32, 27, 29, 26, 30, 27 };
        final int[] v = new int[] { 505, 352, 458, 220, 354, 414, 498, 545, 473, 543 };
        final int n = 10;
        final int W = 0;
        final Knapsack knapsack = new Knapsack();

        assertEquals(0, knapsack.knapsackRec(w, v, n, W));
        assertEquals(0, knapsack.knapsackDP(w, v, n, W));
    }
}

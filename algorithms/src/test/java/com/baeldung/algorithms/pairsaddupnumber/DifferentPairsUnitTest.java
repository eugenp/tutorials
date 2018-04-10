package com.baeldung.algorithms.pairsaddupnumber;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DifferentPairsUnitTest {

    /* All different pairs */

    @Test
    public void getAllDifferentPairsWithForLoop() {
        /* Data */
        final int[] input = {2, 4, 3, 3};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = DifferentPairs.findPairsWithForLoop(input, sum);
        /* Check results */
        assertNotNull(pairs);
        assertEquals(pairs.size(),2);
        assertEquals(pairs.get(0), new Integer(4));
        assertEquals(pairs.get(1),new Integer(3));

    }

    @Test
    public void getAllDifferentPairsWithStreamApi() {
        /* Data */
        final int[] input = {2, 4, 3, 3};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = DifferentPairs.findPairsWithStreamApi(input, sum);
        /* Check results */
        assertNotNull(pairs);
        assertEquals(pairs.size(),2);
        assertEquals(pairs.get(0), new Integer(4));
        assertEquals(pairs.get(1),new Integer(3));
    }
}

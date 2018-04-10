package com.baeldung.algorithms.pairsaddupnumber;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExistingPairsUnitTest {

    /* All existing pairs */

    @Test
    public void getAllExistingPairsWithForLoop() {
        /* Data */
        final int[] input = {2, 4, 3, 3};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = ExistingPairs.findPairsWithForLoop(input, sum);
        /* Check results */
        assertNotNull(pairs);
        assertEquals(pairs.size(),4);
        assertEquals(pairs.get(0), new Integer(2));
        assertEquals(pairs.get(1),new Integer(4));
        assertEquals(pairs.get(2),new Integer(3));
        assertEquals(pairs.get(3),new Integer(3));
    }

    @Test
    public void getAllExistingPairsWithStreamApi() {
        /* Data */
        final int[] input = {2, 4, 3, 3};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = ExistingPairs.findPairsWithStreamApi(input, sum);
        /* Check results */
        assertNotNull(pairs);
        assertEquals(pairs.size(),4);
        assertEquals(pairs.get(0), new Integer(2));
        assertEquals(pairs.get(1),new Integer(4));
        assertEquals(pairs.get(2),new Integer(3));
        assertEquals(pairs.get(3),new Integer(3));
    }
}

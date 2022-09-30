package com.baeldung.pairsaddupnumber;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DifferentPairsUnitTest {

    /* All different pairs */

    @Test
    public void whenTraditionalLoop_thenReturnAllDifferentPairs() {
        /* Data */
        final int[] input = {2, 4, 3, 3, 8};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = DifferentPairs.findPairsWithForLoop(input, sum);
        /* Check results */
        assertThat(pairs).hasSize(2).contains(4,3).doesNotContain(8);
    }

    @Test
    public void whenStreamApi_thenReturnAllDifferentPairs() {
        /* Data */
        final int[] input = {2, 4, 3, 3, 8};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = DifferentPairs.findPairsWithStreamApi(input, sum);
        /* Check results */
        assertNotNull(pairs);
        assertEquals(pairs.size(),2);
        assertEquals(pairs.get(0), new Integer(4));
        assertThat(pairs).hasSize(2).contains(4,3).doesNotContain(8);
    }
}

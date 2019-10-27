package com.baeldung.pairsaddupnumber;

import org.junit.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class ExistingPairsUnitTest {

    /* All existing pairs */

    @Test
    public void whenTraditionalLoop_thenReturnAllExistingPairs() {
        /* Data */
        final int[] input = {2, 4, 3, 3, 8};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = ExistingPairs.findPairsWithForLoop(input, sum);
        /* Check results */
        assertThat(pairs).hasSize(4).contains(2,4,3,3).doesNotContain(8);
    }

    @Test
    public void whenStreamApi_thenReturnAllExistingPairs() {
        /* Data */
        final int[] input = {2, 4, 3, 3, 8};
        final int sum = 6;
        /* Call service */
        final List<Integer> pairs = ExistingPairs.findPairsWithStreamApi(input, sum);
        /* Check results */
        assertThat(pairs).hasSize(4).contains(2,4,3,3).doesNotContain(8);
    }
}

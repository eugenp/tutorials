package com.baeldung.uniquerng;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Test;

public class RngUtilsUnitTest {

    @Test
    public void whenNUniqueNumRequested_thenNUniqueNumConsumed() {
        TreeSet<Integer> set = new TreeSet<>();
        int n = 5;
        UniqueRng rng = new UniqueRng(n, true);

        while (rng.hasNext()) {
            set.add(rng.next());
        }

        assertEquals(n, set.size());
    }

    @Test
    public void givenYRange_whenNUniqueNumRequested_thenNUniqueNumConsumed() {
        TreeSet<Integer> set = new TreeSet<>();
        int n = 5;
        int y = n * 10;

        BigUniqueRng rng = new BigUniqueRng(n, y);
        while (rng.hasNext()) {
            set.add(rng.next());
        }

        assertEquals(n, set.size());
    }

    @Test
    public void givenIntStreamSizeN_whenCollected_thenSetSizeN() {
        int n = 5;
        int from = -5;
        int to = n * 2;

        Random r = new Random();
        Set<Integer> set = r.ints(from, to)
            .distinct()
            .limit(n)
            .boxed()
            .collect(Collectors.toSet());

        assertEquals(n, set.size());
    }
}

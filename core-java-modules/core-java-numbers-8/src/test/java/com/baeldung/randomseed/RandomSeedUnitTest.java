package com.baeldung.randomseed;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

public class RandomSeedUnitTest {
    @Test
    public void whenUsingSameSeeds_thenGenerateNumbers() {
        Random random1 = new Random(35);
        Random random2 = new Random(35);
        int[] numbersFromRandom1 = new int[12];
        int[] numbersFromRandom2 = new int[12];
        for(int i =0 ; i < 12; i++) {
            numbersFromRandom1[i] = random1.nextInt();
            numbersFromRandom2[i] = random2.nextInt();
        }
        assertArrayEquals(numbersFromRandom1, numbersFromRandom2);
    }

    @Test
    public void whenUsingSystemTimeAsSeed_thenGenerateNumbers() {
        long seed = System.nanoTime();
        Random random = new Random(seed);

        for(int i =0 ; i < 10; i++) {
            int randomNumber = random.nextInt(100);
            assertTrue(randomNumber >= 0 && randomNumber < 100);
        }
    }
}

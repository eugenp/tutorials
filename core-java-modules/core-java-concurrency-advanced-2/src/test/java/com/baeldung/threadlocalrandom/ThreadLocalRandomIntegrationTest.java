package com.baeldung.threadlocalrandom;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ThreadLocalRandomIntegrationTest {
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomIntBounded_thenCorrect() {
        int leftLimit = 1;
        int rightLimit = 100;
        int generatedInt = ThreadLocalRandom.current().nextInt(leftLimit, rightLimit);
        
        assertTrue(generatedInt < rightLimit && generatedInt >= leftLimit);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomIntUnbounded_thenCorrect() {
        int generatedInt = ThreadLocalRandom.current().nextInt();
        
        assertTrue(generatedInt < Integer.MAX_VALUE && generatedInt >= Integer.MIN_VALUE);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomLongBounded_thenCorrect() {
        long leftLimit = 1L;
        long rightLimit = 100L;
        long generatedLong = ThreadLocalRandom.current().nextLong(leftLimit, rightLimit);
        
        assertTrue(generatedLong < rightLimit && generatedLong >= leftLimit);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomLongUnbounded_thenCorrect() {
        long generatedInt = ThreadLocalRandom.current().nextLong();
        
        assertTrue(generatedInt < Long.MAX_VALUE && generatedInt >= Long.MIN_VALUE);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomDoubleBounded_thenCorrect() {
        double leftLimit = 1D;
        double rightLimit = 100D;
        double generatedInt = ThreadLocalRandom.current().nextDouble(leftLimit, rightLimit);
        
        assertTrue(generatedInt < rightLimit && generatedInt >= leftLimit);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomDoubleUnbounded_thenCorrect() {
        double generatedInt = ThreadLocalRandom.current().nextDouble();
        
        assertTrue(generatedInt < Double.MAX_VALUE && generatedInt >= Double.MIN_VALUE);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void givenUsingThreadLocalRandom_whenSettingSeed_thenThrowUnsupportedOperationException() {
        ThreadLocalRandom.current().setSeed(0l);
    }

}
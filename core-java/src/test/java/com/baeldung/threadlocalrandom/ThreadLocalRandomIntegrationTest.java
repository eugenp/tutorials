package com.baeldung.threadlocalrandom;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * 测试：ThreadLocalRandom
 */
public class ThreadLocalRandomIntegrationTest {

    /**
     * 说明：ThreadLocalRandom.current().nextInt(leftLimit, rightLimit)为前闭后开区间
     */
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomIntBounded_thenCorrect() {
        int leftLimit = 1;
        int rightLimit = 100;
        int generatedInt = ThreadLocalRandom.current().nextInt(leftLimit, rightLimit);

        System.out.println("generatedInt:{}" + generatedInt);
        assertTrue(generatedInt < rightLimit && generatedInt >= leftLimit);
    }

    /**
     * 说明：System.out.println("generatedInt:{}" + generatedInt)的取值
     * 空间为【Integer.MIN_VALUE，Integer.MAX_VALUE）
     */
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomIntUnbounded_thenCorrect() {
        int generatedInt = ThreadLocalRandom.current().nextInt();

        System.out.println("generatedInt:{}" + generatedInt);
        assertTrue(generatedInt < Integer.MAX_VALUE && generatedInt >= Integer.MIN_VALUE);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomLongBounded_thenCorrect() {
        long leftLimit = 1L;
        long rightLimit = 100L;
        long generatedLong = ThreadLocalRandom.current().nextLong(leftLimit, rightLimit);

        System.out.println("generatedLong:{}" + generatedLong);
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
        double generatedDouble = ThreadLocalRandom.current().nextDouble(leftLimit, rightLimit);

        System.out.println("generatedDouble:{}" + generatedDouble);
        assertTrue(generatedDouble < rightLimit && generatedDouble >= leftLimit);
    }
    
    @Test
    public void givenUsingThreadLocalRandom_whenGeneratingRandomDoubleUnbounded_thenCorrect() {
        double generatedDouble = ThreadLocalRandom.current().nextDouble();
        
        assertTrue(generatedDouble < Double.MAX_VALUE && generatedDouble >= Double.MIN_VALUE);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void givenUsingThreadLocalRandom_whenSettingSeed_thenThrowUnsupportedOperationException() {
        ThreadLocalRandom.current().setSeed(0l);
    }

}
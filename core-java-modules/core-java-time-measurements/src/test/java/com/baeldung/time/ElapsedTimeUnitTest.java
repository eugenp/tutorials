package com.baeldung.time;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

public class ElapsedTimeUnitTest {

    @Test
    public void givenRunningTask_whenMeasuringTimeWithCurrentTimeMillis_thenGetElapsedTime() throws InterruptedException {
        long start = System.currentTimeMillis();
        
        simulateRunningTask();
        
        long finish = System.currentTimeMillis();
        
        long timeElapsed = finish - start;
        
        assertEquals(true, (2000L <= timeElapsed) && (timeElapsed <= 3000L));
    }
    
    @Test
    public void giveRunningTask_whenMeasuringTimeWithNanoTime_thenGetElapsedTime() throws InterruptedException {
        long start = System.nanoTime();
        
        simulateRunningTask();
        
        long finish = System.nanoTime();
        
        long timeElapsed = finish - start;
        
        assertEquals(true, (2000000000L <= timeElapsed) && (timeElapsed <= 3000000000L));
    }
    
    @Test
    public void givenRunningTask_whenMeasuringTimeWithStopWatch_thenGetElapsedTime() throws InterruptedException {
        StopWatch watch = new StopWatch();        
        watch.start();

        simulateRunningTask();
        
        watch.stop();
        
        long timeElapsed = watch.getTime();
        
        assertEquals(true, (2000L <= timeElapsed) && (timeElapsed <= 3000L));
    }

    /*
     The below test depends on the elapsed time, which isn't ideal in a test.
     Also, it slows down test execution artificially.
     */
    /*@Test
    public void givenRunningTask_whenMeasuringTimeWithInstantClass_thenGetElapsedTime() throws InterruptedException {
        Instant start = Instant.now();
        System.out.println("start: " + start);

        simulateRunningTask();

        Instant finish = Instant.now();

        System.out.println("start: " + start);
        System.out.println("finish: " + finish);
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("elapsed: " + timeElapsed);
        assertEquals(true, (2000L <= timeElapsed) && (timeElapsed <= 3000L));
    }*/
    
    /**
     * Simulate task running for 2.5 seconds.
     * 
     * @throws InterruptedException
     */
    private static void simulateRunningTask() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(2500); // 2.5 seconds

    } 
}

package com.baeldung.concurrent.threadjoin;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Demonstrates Thread.join behavior.
 *
 */
public class ThreadJoinUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadJoinUnitTest.class);

    static class SampleThread extends Thread {
        public int processingCount;

        SampleThread(int processingCount) {
            this.processingCount = processingCount;
            LOGGER.info("Thread " + this.getName() + " created");
        }

        @Override
        public void run() {
            LOGGER.info("Thread " + this.getName() + " started");
            while (processingCount > 0) {
                try {
                    Thread.sleep(1000); // Simulate some work being done by thread
                } catch (InterruptedException e) {
                    LOGGER.info("Thread " + this.getName() + " interrupted.");
                }
                processingCount--;
                LOGGER.info("Inside Thread " + this.getName() + ", processingCount = " + processingCount);
            }
            LOGGER.info("Thread " + this.getName() + " exiting");
        }
    }

    @Test
    public void givenNewThread_whenJoinCalled_returnsImmediately() throws InterruptedException {
        Thread t1 = new SampleThread(0);
        LOGGER.info("Invoking join");
        t1.join();
        LOGGER.info("Returned from join");
        LOGGER.info("Thread state is" + t1.getState());
        assertFalse(t1.isAlive());
    }

    @Test
    public void givenStartedThread_whenJoinCalled_waitsTillCompletion() throws InterruptedException {
        Thread t2 = new SampleThread(1);
        t2.start();
        LOGGER.info("Invoking join");
        t2.join();
        LOGGER.info("Returned from join");
        assertFalse(t2.isAlive());
    }

    @Test
    public void givenStartedThread_whenTimedJoinCalled_waitsUntilTimedout() throws InterruptedException {
        Thread t3 = new SampleThread(10);
        t3.start();
        t3.join(1000);
        assertTrue(t3.isAlive());
    }

    @Test
    @Ignore
    public void givenThreadTerminated_checkForEffect_notGuaranteed() throws InterruptedException {
        SampleThread t4 = new SampleThread(10);
        t4.start();
        //not guaranteed to stop even if t4 finishes.
        do {

        } while (t4.processingCount > 0);
    }

    @Test
    public void givenJoinWithTerminatedThread_checkForEffect_guaranteed() throws InterruptedException {
        SampleThread t4 = new SampleThread(10);
        t4.start();
        do {
            t4.join(100);
        } while (t4.processingCount > 0);
    }

}
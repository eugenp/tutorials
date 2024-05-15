package com.baeldung.concurrent.stopexecution;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LongRunningTaskUnitTest {

    @Test
    public void run() {
        Thread thread = new Thread(new LongRunningTask());
        thread.start();
        assertTrue(thread.isAlive());

        thread.interrupt();
        assertTrue(thread.isInterrupted());
    }
}
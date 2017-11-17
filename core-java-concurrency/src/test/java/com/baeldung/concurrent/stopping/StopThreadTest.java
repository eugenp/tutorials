package com.baeldung.concurrent.stopping;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StopThreadTest {

    @Test
    public void whenStoppedThreadIsStopped() throws InterruptedException {

        int interval = 100;

        ControlSubThread controlSubThread = new ControlSubThread(interval);
        controlSubThread.start();

        // Give things a chance to get set up
        Thread.sleep(interval);
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.stop();
        Thread.sleep(interval);
        assertTrue(controlSubThread.isStopped());
    }


    @Test
    public void whenInterruptedThreadIsStopped() throws InterruptedException {

        int interval = 5000;

        ControlSubThread controlSubThread = new ControlSubThread(interval);
        controlSubThread.start();

        // Give things a chance to get set up
        Thread.sleep(100);
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.interrupt();

        // Wait less than the time we would normally sleep, and make sure we exited.
        Thread.sleep(interval/10);
        assertTrue(controlSubThread.isStopped());
    }
}

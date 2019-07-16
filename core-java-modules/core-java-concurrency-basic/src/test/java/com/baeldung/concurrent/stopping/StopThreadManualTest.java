package com.baeldung.concurrent.stopping;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.jayway.awaitility.Awaitility;

public class StopThreadManualTest {

    @Test
    public void whenStoppedThreadIsStopped() throws InterruptedException {

        int interval = 5;

        ControlSubThread controlSubThread = new ControlSubThread(interval);
        controlSubThread.start();

        // Give things a chance to get set up
        Thread.sleep(interval);
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.stop();
        await()
          .until(() -> assertTrue(controlSubThread.isStopped()));
    }

    @Test
    public void whenInterruptedThreadIsStopped() throws InterruptedException {

        int interval = 50;

        ControlSubThread controlSubThread = new ControlSubThread(interval);
        controlSubThread.start();

        // Give things a chance to get set up
        Thread.sleep(interval);
        assertTrue(controlSubThread.isRunning());
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.interrupt();

        // Wait less than the time we would normally sleep, and make sure we exited.
        Awaitility.await()
            .pollDelay(2, TimeUnit.MILLISECONDS)
          .atMost(interval/ 10, TimeUnit.MILLISECONDS)
          .until(controlSubThread::isStopped);
    }
}

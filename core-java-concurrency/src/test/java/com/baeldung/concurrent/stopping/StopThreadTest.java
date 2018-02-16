package com.baeldung.concurrent.stopping;

import static com.jayway.awaitility.Awaitility.await;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StopThreadTest {

    @Test
    public void whenStoppedThreadIsStopped() throws InterruptedException {

        ControlSubThread controlSubThread = new ControlSubThread();
        controlSubThread.start();

        // Give things a chance to get set up
        assertIsStarted(controlSubThread);
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.stop();
        assertIsStopped(controlSubThread);
    }

    @Test
    public void whenInterruptedThreadIsStopped() throws InterruptedException {

        ControlSubThread controlSubThread = new ControlSubThread();
        controlSubThread.start();

        // Give things a chance to get set up
        assertIsStarted(controlSubThread);
        assertFalse(controlSubThread.isStopped());

        // Stop it and make sure the flags have been reversed
        controlSubThread.interrupt();

        // Wait less than the time we would normally sleep, and make sure we exited.
        assertIsStopped(controlSubThread);
    }

    private void assertIsStarted(ControlSubThread controlSubThread) {
        await().until(() -> assertTrue(controlSubThread.isRunning()));
    }

    private void assertIsStopped(ControlSubThread controlSubThread) {
        await().until(() -> assertTrue(controlSubThread.isStopped()));
    }
}

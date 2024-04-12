package com.baeldung.concurrent.stopexecution;

import org.junit.Test;

import java.util.Timer;

import static org.junit.Assert.assertTrue;

public class TimeOutTaskUnitTest {

    @Test
    public void run() {
        Thread thread = new Thread(new LongRunningTask());
        Timer timer = new Timer();
        TimeOutTask timeOutTask = new TimeOutTask(thread, timer);
        thread.start();
        timeOutTask.run();
        assertTrue(thread.isInterrupted());
    }
}
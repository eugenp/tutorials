package org.baeldung.java;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

public class JavaTimerUnitTest {

    // tests

    @Test
    public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() throws InterruptedException {
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task performed on " + new Date() + "\n" + "Thread's name: " + Thread.currentThread().getName());
            }
        };

        final Timer timer = new Timer("Timer");
        final long delay = 1000L;
        timer.schedule(timerTask, delay);

        Thread.sleep(delay * 2);
        timer.cancel();
    }

}

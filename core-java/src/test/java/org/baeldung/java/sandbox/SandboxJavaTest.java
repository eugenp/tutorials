package org.baeldung.java.sandbox;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

public class SandboxJavaTest {

    @Test
    public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() throws InterruptedException {
        final Timer timer = new Timer("Thread's name");
        System.out.println("Current time:" + new Date());
        System.out.println("Thread name: " + Thread.currentThread().getName());

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time when task performed" + new Date());
                System.out.println("Thread name: " + Thread.currentThread().getName());
                timer.cancel();
            }
        };
        final long delay = 2 * 1000;
        timer.schedule(timerTask, delay);
    }

}

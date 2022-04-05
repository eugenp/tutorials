package com.baeldung.sandbox;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SandboxJavaManualTest {


    private static final Logger LOG = LoggerFactory.getLogger(SandboxJavaManualTest.class);

    @Test
    public void givenUsingTimer_whenSchedulingTimerTaskOnce_thenCorrect() throws InterruptedException {
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                LOG.debug("Time when was task performed" + new Date());
                LOG.debug("Thread's name: " + Thread.currentThread().getName());
            }
        };
        final Timer timer = new Timer("Thread's name");
        LOG.debug("Current time:" + new Date());
        LOG.debug("Thread's name: " + Thread.currentThread().getName());
        final long delay = 2L * 1000L;
        timer.schedule(timerTask, delay);
        Thread.sleep(delay);
    }

    @Test
    public void givenUsingTimer_whenSchedulingRepeatedTask_thenCorrect() throws InterruptedException {
        final TimerTask repeatedTask = new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                count++;
                LOG.debug("Time when task was performed: " + new Date());
                LOG.debug("Thread's name: " + Thread.currentThread().getName());
                if (count >= 5) {
                    cancel();
                }
            }
        };
        final Timer timer = new Timer("Timer thread");
        LOG.debug("Current time: " + new Date());
        LOG.debug("Thread's name: " + Thread.currentThread().getName());
        final long delay = 2L * 1000L;
        final long period = 1L * 1000L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        Thread.sleep(delay + period * 5L);
    }

    @Test
    public void givenUsingTimer_whenSchedulingRepeatedCustomTimerTask_thenCorrect() throws InterruptedException {
        class MyTask extends TimerTask {
            long timesToRun = 0;
            long timesRunned = 0;

            public void setTimesToRun(final int timesToRun) {
                this.timesToRun = timesToRun;
            }

            @Override
            public void run() {
                timesRunned++;
                LOG.debug("Task performed on: " + new Date());
                LOG.debug("Thread's name: " + Thread.currentThread().getName());
                if (timesRunned >= timesToRun) {
                    cancel();
                }
            }
        }
        final MyTask repeatedTask = new MyTask();
        repeatedTask.setTimesToRun(5);
        final long delay = 2L * 1000L;
        final long period = 1000L;
        final Timer timer = new Timer("Timer");
        LOG.debug("Current time: " + new Date());
        LOG.debug("Thread's name: " + Thread.currentThread().getName());
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
        Thread.sleep(delay + period * repeatedTask.timesToRun);
    }

}

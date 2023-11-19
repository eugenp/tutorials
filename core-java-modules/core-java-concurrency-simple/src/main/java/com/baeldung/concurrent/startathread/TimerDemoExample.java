package com.baeldung.concurrent.startathread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemoExample {

    private void scheduleOnce() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "\n" + "Thread's name: " + Thread.currentThread()
                    .getName());
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        timer.schedule(task, delay);
    }

    private void scheduleRecurrently() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Recurrent Task performed on: " + new Date() + "\n" + "Thread's name: " + Thread.currentThread()
                    .getName());
            }
        };
        Timer timer = new Timer("Timer");
        long delay = 1000L;
        final long period = 1000L;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public static void main(String[] args) {
        TimerDemoExample timerDemoExample = new TimerDemoExample();
        timerDemoExample.scheduleOnce();
        timerDemoExample.scheduleRecurrently();
    }
}

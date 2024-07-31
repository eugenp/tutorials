package com.baeldung.concurrent.delay;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Delay {

    public static void main(String args[]) throws InterruptedException {
        
        threadSleep(4, 1);

        timeunitSleep(4, 1);

        delayedServiceTask(5);

        fixedRateServiceTask(5);

        System.out.println("Done.");

        return;

    }

    private static void threadSleep(Integer iterations, Integer secondsToSleep) {

        for (Integer i = 0; i < iterations; i++) {

            System.out.println("This is loop iteration number " + i.toString());

            try {
                Thread.sleep(secondsToSleep * 1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

        }

    }

    private static void timeunitSleep(Integer iterations, Integer secondsToSleep) {

        for (Integer i = 0; i < iterations; i++) {

            System.out.println("This is loop iteration number " + i.toString());

            try {
                TimeUnit.SECONDS.sleep(secondsToSleep);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

        }

    }

    private static void delayedServiceTask(Integer delayInSeconds) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.schedule(Delay::someTask1, delayInSeconds, TimeUnit.SECONDS);

        executorService.shutdown();
    }

    private static void fixedRateServiceTask(Integer delayInSeconds) {

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        ScheduledFuture<?> sf = executorService.scheduleAtFixedRate(Delay::someTask2, 0, delayInSeconds,
                TimeUnit.SECONDS);

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        sf.cancel(true);

        executorService.shutdown();
    }

    private static void someTask1() {
        System.out.println("Task 1 completed.");
    }

    private static void someTask2() {
        System.out.println("Task 2 completed.");
    }

}
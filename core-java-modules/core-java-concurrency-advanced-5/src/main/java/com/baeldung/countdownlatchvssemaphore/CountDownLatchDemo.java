package com.baeldung.countdownlatchvssemaphore;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        // Create a CountDownLatch with an initial count equal to the number of tasks to be completed
        int numberOfTasks = 3;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        // Simulate completion of tasks by worker threads
        for (int i = 1; i <= numberOfTasks; i++) {
            new Thread(() -> {
                System.out.println("Task completed by Thread " + Thread.currentThread()
                    .getId());

                // Decrement the latch count to signal completion of a task
                latch.countDown();
            }).start();
        }

        // Main thread waits until all tasks are completed
        latch.await();
        System.out.println("All tasks completed. Main thread proceeds.");

        // Attempting to reset will have no effect
        latch.countDown();
        // Latch is already at zero, await() returns immediately
        latch.await(); // This line won't block
        System.out.println("Latch is already at zero and cannot be reset.");
    }
}

package com.baeldung.countdownlatchvssemaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    public static void main(String[] args) {
        // Create a Semaphore with a fixed number of permits
        int NUM_PERMITS = 3;
        Semaphore semaphore = new Semaphore(NUM_PERMITS);

        // Simulate resource access by worker threads
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                try {
                    // Acquire a permit to access the resource
                    semaphore.acquire();
                    System.out.println("Thread " + Thread.currentThread().getId() + " accessing resource.");

                    // Simulate resource usage
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Release the permit after resource access is complete
                    semaphore.release();
                }
            }).start();
        }

        // Simulate resetting the Semaphore by releasing additional permits after a delay
        try {
            Thread.sleep(5000);

            // Resetting the semaphore permits to the initial count
            semaphore.release(NUM_PERMITS);
            System.out.println("Semaphore permits reset to initial count.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
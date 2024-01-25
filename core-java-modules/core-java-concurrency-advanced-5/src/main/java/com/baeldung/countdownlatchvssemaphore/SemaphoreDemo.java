package com.baeldung.countdownlatchvssemaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    private static final int NUM_PERMITS = 3;
    private static final int NUM_THREADS = 5;
    private static Semaphore semaphore = new Semaphore(NUM_PERMITS);

    public static void main(String[] args) {
        // Create and start worker threads
        for (int i = 1; i <= NUM_THREADS; i++) {
            Thread workerThread = new Thread(new Worker(i));
            workerThread.start();
        }
    }

    // Worker thread simulating resource access
    private static class Worker implements Runnable {

        private final int workerId;

        public Worker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            try {
                // Simulate work
                System.out.println("Worker " + workerId + " is waiting to access a resource.");
                semaphore.acquire();
                System.out.println("Worker " + workerId + " has acquired a resource.");
                Thread.sleep(2000);
                System.out.println("Worker " + workerId + " has released the resource.");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
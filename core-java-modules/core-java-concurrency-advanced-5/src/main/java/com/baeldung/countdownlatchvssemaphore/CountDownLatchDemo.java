package com.baeldung.countdownlatchvssemaphore;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        final int tasks = 5;
        CountDownLatch latch = new CountDownLatch(tasks);

        // Create and start worker threads
        for (int i = 1; i <= tasks; i++) {
            new Thread(new Worker(latch, i)).start();
        }

        System.out.println("Main thread waiting for workers to complete...");
        latch.await();

        System.out.println("All tasks completed! Main thread proceeding...");
    }

    private static class Worker implements Runnable {

        private final CountDownLatch latch;
        private final int taskId;

        public Worker(CountDownLatch latch, int taskId) {
            this.latch = latch;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            try {
                // Simulate some work
                Thread.sleep(1000 + (int) (Math.random() * 2000)); // Random delay for each task
                System.out.println("Task " + taskId + " completed!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Signal task completion
                latch.countDown();
            }
        }
    }
}

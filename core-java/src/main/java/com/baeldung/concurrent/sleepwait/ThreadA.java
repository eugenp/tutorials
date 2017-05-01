package com.baeldung.concurrent.sleepwait;

/***
 * Example of waking up a waiting thread
 */
public class ThreadA {
    private static final ThreadB b = new ThreadB();

    public static void main(String... args) throws InterruptedException {
        b.start();

        synchronized (b) {
            while (b.sum == 0) {
                System.out.println("Waiting for ThreadB to complete...");
                b.wait();
            }

            System.out.println("ThreadB has completed. Sum from that thread is: " + b.sum);
        }
    }
}

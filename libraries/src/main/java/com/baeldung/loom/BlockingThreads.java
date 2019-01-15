package com.baeldung.loom;

public class BlockingThreads {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println("Hello " + Thread.currentThread().getName());
                LOCK.notify();
            }
        });


        Thread thread2 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("Will wait for thread1 now...");
                    LOCK.wait();
                    System.out.println("World " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.start();
        thread1.start();

    }
}

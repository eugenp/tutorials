package com.baeldung.ownablesynchronizers;

import java.util.concurrent.locks.ReentrantLock;

public class Application {

    static ReentrantLock firstLock = new ReentrantLock(true);
    static ReentrantLock secondLock = new ReentrantLock(true);

    public static void main(String[] args) throws Exception {
        // Create first thread that will lock on 'firstLock'
        Thread first = new Thread(() -> {
            try {
                firstLock.lock();

                // synchronized block here allows us to wait for other thread to lock,
                // in order to simulate a deadlock
                synchronized (Application.class) {
                    Application.class.notify();
                    if (!secondLock.isLocked()) {
                        Application.class.wait();
                    }
                }

                // thread will hang here
                secondLock.lock();

                System.out.println("Thread 1: Finished");
                firstLock.unlock();
                secondLock.unlock();
            } catch (Exception e) {

            }
        });

        // Second thread will lock on 'secondLock'
        Thread second = new Thread(() -> {
            try {
                secondLock.lock();

                // synchronized block here allows us to wait for other thread to lock,
                // in order to simulate a deadlock
                synchronized (Application.class) {
                    Application.class.notify();
                    if (!firstLock.isLocked()) {
                        Application.class.wait();
                    }
                }

                // thread will hang here
                firstLock.lock();

                System.out.println("Thread 2: Finished");
                firstLock.unlock();
                secondLock.unlock();
            } catch (Exception e) {
            }
        });

        first.start();
        second.start();

        first.join();
        second.join();
    }
}

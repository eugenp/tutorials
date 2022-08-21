package com.baeldung.ownablesynchronizers;

import java.util.concurrent.locks.ReentrantLock;

public class Application {

    public static Thread createThread(ReentrantLock mainLock, ReentrantLock otherLock, String threadName) {
        return new Thread(() -> {
            try {
                mainLock.lock();

                // synchronized block here allows us to wait for other thread to lock,
                // in order to simulate a deadlock
                synchronized (Application.class) {
                    Application.class.notify();
                    if (!otherLock.isLocked()) {
                        Application.class.wait();
                    }
                }

                // thread will hang here
                otherLock.lock();

                System.out.println(threadName + ": Finished");
                mainLock.unlock();
                otherLock.unlock();
            } catch (InterruptedException e) {
                System.out.println(threadName + ": " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock firstLock = new ReentrantLock(true);
        ReentrantLock secondLock = new ReentrantLock(true);
        Thread first = createThread(firstLock, secondLock, "Thread-0");
        Thread second = createThread(secondLock, firstLock, "Thread-1");

        first.start();
        second.start();

        first.join();
        second.join();
    }
}

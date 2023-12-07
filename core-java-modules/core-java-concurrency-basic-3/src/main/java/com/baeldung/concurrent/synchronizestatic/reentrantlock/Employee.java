package com.baeldung.concurrent.synchronizestatic.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Synchronizing static variable with a Reenatrant Lock.
 */
public class Employee {

    private static final ReentrantLock lock = new ReentrantLock();

    static int count;
    int id;
    String name;
    String title;

    public Employee(int id, String name, String title) {
        incrementCount();
        this.id = id;
        this.name = name;
        this.title = title;
    }

    private static void incrementCount() {
        lock.lock();
        try {
            System.out.println("Count = " + ++count);
        }
        finally {
            lock.unlock();
        }
    }

    public static int getCount() {
        lock.lock();
        try {
            return count;
        }
        finally {
            lock.unlock();
        }
    }
}

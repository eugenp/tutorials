package com.baeldung.concurrent.synchronizestatic.atomicinteger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Synchronizing static variable with AtomicInteger.
 */
public class Employee {

    private static final AtomicInteger count = new AtomicInteger(0);

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
        count.incrementAndGet();
    }

    public static int getCount() {
        return count.get();
    }
}

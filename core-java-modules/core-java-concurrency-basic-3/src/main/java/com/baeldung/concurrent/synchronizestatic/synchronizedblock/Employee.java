package com.baeldung.concurrent.synchronizestatic.synchronizedblock;

/**
 * Synchronizing static variable with a synchronized block.
 */
public class Employee {

    private static final Object lock = new Object();

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
        synchronized(lock) {
            System.out.println("Count = " + ++count);
        }
    }

    public static int getCount() {
        synchronized(lock) {
            return count;
        }
    }
}

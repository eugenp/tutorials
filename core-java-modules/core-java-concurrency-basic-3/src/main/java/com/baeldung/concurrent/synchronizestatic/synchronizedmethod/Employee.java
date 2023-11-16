package com.baeldung.concurrent.synchronizestatic.synchronizedmethod;

/**
 * Synchronizing static variable with a synchronized method.
 */
public class Employee {
    static int count;
    int id;
    String name;
    String title;

    public Employee(int id, String name, String title)
    {
        incrementCount();
        this.id = id;
        this.name = name;
        this.title = title;
    }

    private static synchronized void incrementCount() {
        System.out.println("Count = " + ++count);
    }

    public static synchronized int getCount() {
        return count;
    }
}

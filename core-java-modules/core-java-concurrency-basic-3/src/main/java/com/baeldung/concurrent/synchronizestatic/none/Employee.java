package com.baeldung.concurrent.synchronizestatic.none;

/**
 * No synchronization.
 */
public class Employee {

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
        System.out.println("Count = " + ++count);
    }

    public static Integer getCount() {
        return count;
    }
}

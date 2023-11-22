package com.baeldung.concurrent.synchronizestatic.synchronizedclass;

/**
 * Synchronizing static variable with a synchronized block.
 */
public class Employee
{
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
        synchronized(Employee.class) {
            System.out.println("Count = " + ++count);
        }
    }

    public static int getCount() {
        synchronized(Employee.class) {
            return count;
        }
    }
}

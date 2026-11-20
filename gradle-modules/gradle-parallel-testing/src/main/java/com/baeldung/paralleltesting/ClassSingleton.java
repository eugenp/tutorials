package com.baeldung.paralleltesting;

public final class ClassSingleton {

    public String info = "Initial info class";
    private static ClassSingleton INSTANCE;

    private static int count = 0;

    private ClassSingleton() {
    }

    public static ClassSingleton getINSTANCE() {
        return INSTANCE;
    }

    public int getCount() {
        return count;
    }

    public static ClassSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClassSingleton();
        }
        count++;
        return INSTANCE;
    }

    // more features below ...
}
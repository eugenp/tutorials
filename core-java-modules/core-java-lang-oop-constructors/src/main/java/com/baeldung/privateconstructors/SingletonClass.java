package com.baeldung.privateconstructors;

public final class SingletonClass {

    private static SingletonClass INSTANCE;
    private String info = "Initial info class";

    private SingletonClass() {
    }

    public static SingletonClass getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonClass();
        }

        return INSTANCE;
    }

    // getters and setters
}
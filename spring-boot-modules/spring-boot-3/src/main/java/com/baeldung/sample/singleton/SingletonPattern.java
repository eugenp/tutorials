package com.baeldung.sample.singleton;

public final class SingletonPattern {

    private static SingletonPattern instance = null;

    private SingletonPattern() {}

    public static SingletonPattern getInstance() {
        if (instance == null) {
            instance = new SingletonPattern();
        }
        return instance;
    }

    public String getValue() {
        return "test";
    }

}

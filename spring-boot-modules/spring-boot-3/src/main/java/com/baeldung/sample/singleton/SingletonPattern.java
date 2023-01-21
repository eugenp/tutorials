package com.baeldung.sample.singleton;

public final class SingletonPattern {

    private static volatile SingletonPattern instance = null;

    private SingletonPattern() {}

    public static SingletonPattern getInstance() {
        if (instance == null) {
            synchronized(SingletonPattern.class) {
                if (instance == null) {
                    instance = new SingletonPattern();
                }
            }
        }
        return instance;
    }

    public String getValue() {
        return "test";
    }

}

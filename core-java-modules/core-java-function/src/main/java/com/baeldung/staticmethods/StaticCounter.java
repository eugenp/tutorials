package com.baeldung.staticmethods;

public class StaticCounter {

    private static int counter = 0;

    public static int incrementCounter() {
        return ++counter;
    }

    public static int getCounterValue() {
        return counter;
    }

}

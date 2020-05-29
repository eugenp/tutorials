package com.baeldung.hexagon;

import com.baeldung.hexagon.application.Application;

public class Main {

    public static final String JOHN = "John";
    public static final String BOB = "Bob";

    public static void main(String[] args) {
        Application application = new Application();

        application.registerConsoleClient(BOB, System.out::println);
        application.registerConsoleClient(JOHN, System.out::println);

        application.postMessage(BOB, JOHN, "Hey John!");
        application.postMessage(JOHN, BOB, "Howdy Bob!");
    }
}

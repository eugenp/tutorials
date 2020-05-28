package com.baeldung.hexagon;

import com.baeldung.hexagon.adapters.ClientIOAdapter;
import com.baeldung.hexagon.application.Application;

public class Main {

    public static final String JOHN = "John";
    public static final String BOB = "Bob";

    public static void main(String[] args) {
        Application application = new Application();

        ClientIOAdapter john = application.registerConsoleClient(JOHN, System.out::println);
        ClientIOAdapter bob = application.registerConsoleClient(BOB, System.out::println);

        john.post(BOB, "Hey Bob!");
        bob.post(JOHN, "How you doing John?");
        john.post("Nobody", "This message will not be printed, but will be stored.");
    }
}

package com.baeldung.hexagonal;

import java.util.Map;

import com.baeldung.hexagonal.input.ConsoleUI;

public class AccountApplication extends IOCContainer {

    public static void main(String[] args) {

        AccountApplication app = new AccountApplication();

        ConsoleUI console = app.get(ConsoleUI.class);
        console.printWelcomeMessage();

        Map<String, String> parameters = console.readParameters();
        console.create(parameters);

    }

}

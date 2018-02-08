package com.baeldung.system;

import java.io.Console;

public class SystemConsoleDemo {
    public static void main(String[] args) {
        Console console = System.console();
        String name = "";

        if (console != null) {
            name = console.readLine("%s", "Enter your name: ");
            char[] password = console.readPassword("%s", "Password: ");

            console.printf("Hello %s", name);
        } else {
            System.out.println("Console not available.");
        }
    }
}

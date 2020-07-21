package com.baeldung.consoleout;

import java.io.Console;

public class ConsoleAndOut {
    public static void main(String[] args) {
        Console console = System.console();
        System.out.println(console);
        
        if (console != null) {
            char[] password = console.readPassword("Enter password:");
            console.printf(String.valueOf(password));
        }
    }
}

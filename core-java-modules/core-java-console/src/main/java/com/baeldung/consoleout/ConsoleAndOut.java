package com.baeldung.consoleout;

import java.io.Console;

public class ConsoleAndOut {
    public static void main(String[] args) {
        Console console = System.console();
        console.writer().print(console);
        
        char[] password = console.readPassword("Enter password:");
        console.printf(String.valueOf(password));
        
        System.out.println(System.out);
    }
}

package com.baeldung.consoleout;

import java.io.Console;

public class ConsoleAndOut {
    public static void main(String[] args) {
        try {
            printConsoleObject();
            readPasswordFromConsole();
        } catch (Exception ex) {
            // Eating NullPointerExcpetion which will occur when this 
            // program will be run from mediums other than console
        }
        printSysOut();    
    }

    static void printConsoleObject() {
        Console console = System.console();
        console.writer().print(console);
    }

    static void readPasswordFromConsole() {
        Console console = System.console();
        char[] password = console.readPassword("Enter password: ");
        console.printf(String.valueOf(password));
    }
    
    static void printSysOut() {
        System.out.println(System.out);
    }
}

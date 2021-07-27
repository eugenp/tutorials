package com.baeldung.hexagonal.outside.driving.console;

import java.util.Formatter;
import java.util.Scanner;

public class ConsoleApp {
    private final UserServiceAdapter adapter;

    public ConsoleApp(UserServiceAdapter adapter) {
        this.adapter = adapter;
    }

    public void run() {
        printUsage();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String line = in.nextLine();
            if ("help".equalsIgnoreCase(line)) {
                printUsage();
            }
            else if ("quit".equalsIgnoreCase(line)) {
                break;
            }
            else {
                String response = adapter.process(line);
                System.out.println(response);
            }
        }
    }

    public static void printUsage() {
        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        fm.format("Welcome to our User Service Console%n");
        fm.format("Usage:%n");
        fm.format("- add <username>: add new user%n");
        fm.format("- help: print usage%n");
        fm.format("- list: list all users%n");
        fm.format("- quit: exit user service%n");
        System.out.println(sb);
    }
}
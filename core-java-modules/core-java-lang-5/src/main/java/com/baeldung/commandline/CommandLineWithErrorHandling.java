package com.baeldung.commandline;

public class CommandLineWithErrorHandling {

    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println(args[0]);
        } else {
            System.out.println("No command line arguments were provided.");
        }
    }
}
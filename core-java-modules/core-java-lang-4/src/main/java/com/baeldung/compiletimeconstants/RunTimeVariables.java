package com.baeldung.compiletimeconstants;

import java.io.Console;
public class RunTimeVariables {

    public static void main(String[] args) {
        Console console = System.console();
        final String input = console.readLine();
        console.writer().write(input);
        final double random = Math.random();
        console.writer().write("Number: " + random);
    }

}

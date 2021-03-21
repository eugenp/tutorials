package com.baeldung.finalkeyword;

import java.io.Console;

public class ClassVariableNonFinal {

    static boolean doX = false;
    static boolean doY = true;

    public static void main(String[] args) {
        Console console = System.console();
        if (doX) {
            console.writer().println("x");
        } else if (doY) {
            console.writer().println("y");
        }
    }

}

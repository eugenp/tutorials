package com.baeldung.compiletimeconstants;

import java.io.PrintWriter;

public class CompileTimeVariables {

    public final int MAXIMUM_LOGIN_ATTEMPTS = 5;

    public static void main(String[] args) {
        PrintWriter printWriter = System.console().writer();
        CompileTimeVariables instance = new CompileTimeVariables();
        final String username = "baeldung";
        printWriter.println(username);
        printWriter.println(instance.MAXIMUM_LOGIN_ATTEMPTS);
    }

}

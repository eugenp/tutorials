package com.baeldung.compiletimeconstants;

import java.io.PrintWriter;

public class CompileTimeVariables {

    public String ERROR_MESSAGE = ClassConstants.DEFAULT_USERNAME + " not allowed here.";
    public final int maximumLoginAttempts = 5 * 5;

    public static void main(String[] args) {
        PrintWriter printWriter = System.console().writer();
        printWriter.println(ClassConstants.DEFAULT_USERNAME);

        CompileTimeVariables instance = new CompileTimeVariables();
        printWriter.println(instance.maximumLoginAttempts);

        final String username = "baeldung" + "-" + "user";
        printWriter.println(username);
    }

}

package com.baeldung.compiletimeconstants;

import java.io.PrintWriter;

public class CompileTimeVariables {

    public String ERROR_MESSAGE = ClassConstants.DEFAULT_USERNAME + " not allowed here.";
    public final int MAXIMUM_LOGIN_ATTEMPTS = 5;

    public static void main(String[] args) {
        PrintWriter printWriter = System.console().writer();
        printWriter.write(ClassConstants.DEFAULT_USERNAME);

        CompileTimeVariables instance = new CompileTimeVariables();
        printWriter.println(instance.MAXIMUM_LOGIN_ATTEMPTS);

        final String username = "baeldung";
        printWriter.println(username);
    }

}

package com.baeldung.designpatterns.chainofresponsibility;

import java.io.PrintStream;

public class ErrorLogger extends AbstractLogger {
    public ErrorLogger(int level, AbstractLogger nextLogger) {
        super(level, nextLogger);
    }

    @Override
    public void write(String message, PrintStream ps) {
        ps.println("ERROR::Logger: " + message);
    }
}

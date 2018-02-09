package com.baeldung.designpatterns.chainofresponsibility;

import java.io.PrintStream;

public class DebugLogger extends AbstractLogger {
    public DebugLogger(int level, AbstractLogger nextLogger) {
        super(level, nextLogger);
    }

    @Override
    public void write(String message, PrintStream ps) {
        ps.println("DEBUG::Logger: " + message);
    }
}

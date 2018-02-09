package com.baeldung.designpatterns.chainofresponsibility;

import java.io.PrintStream;

public class InfoLogger extends AbstractLogger {
    public InfoLogger(int level, AbstractLogger nextLogger) {
        super(level, nextLogger);
    }

    @Override
    public void write(String message, PrintStream ps) {
        ps.println("INFO::Logger: " + message);
    }
}

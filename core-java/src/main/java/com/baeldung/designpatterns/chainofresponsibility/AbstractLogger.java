package com.baeldung.designpatterns.chainofresponsibility;

import java.io.PrintStream;

public abstract class AbstractLogger {
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int ERROR = 3;

    public int level;
    public PrintStream ps = System.out;

    // next element in chain or responsibility
    public AbstractLogger nextLogger;

    public AbstractLogger(int level, AbstractLogger nextLogger) {
        this.level = level;
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (this.level == level) {
            write(message, ps);
        } else if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        } else {
            // write message to console
        }
    }

    public abstract void write(String message, PrintStream ps);
}

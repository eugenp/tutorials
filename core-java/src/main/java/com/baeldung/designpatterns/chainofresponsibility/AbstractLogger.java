package com.baeldung.designpatterns.chainofresponsibility;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractLogger {
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int ERROR = 3;

    public int level;
    public OutputStream os = System.out;

    // next element in chain or responsibility
    public AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) throws IOException{
        if (this.level == level) {
            write(message, os);
        } else if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        } else {
            // write message to console
        }
    }

    public abstract void write(String message, OutputStream os) throws IOException;
}

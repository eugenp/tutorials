package com.baeldung.designpatterns.chainofresponsibility;

public abstract class AbstractLogger {
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int ERROR = 3;

    protected int level;

    // next element in chain or responsibility
    protected AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public String logMessage(int level, String message) {
        if (this.level == level) {
            return write(message);
        } else if (nextLogger != null) {
            return nextLogger.logMessage(level, message);
        } else {
            return "CONSOLE::Logger: " + message;
        }
    }

    protected abstract String write(String message);
}

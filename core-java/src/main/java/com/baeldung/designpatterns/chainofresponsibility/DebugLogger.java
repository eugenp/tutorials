package com.baeldung.designpatterns.chainofresponsibility;

public class DebugLogger extends AbstractLogger {
    public DebugLogger(int level) {
        this.level = level;
    }

    @Override
    protected String write(String message) {
        return "DEBUG::Logger: " + message;
    }
}

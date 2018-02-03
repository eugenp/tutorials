package com.baeldung.designpatterns.chainofresponsibility;

public class InfoLogger extends AbstractLogger {
    public InfoLogger(int level) {
        this.level = level;
    }

    @Override
    protected String write(String message) {
        return "INFO::Logger: " + message;
    }
}

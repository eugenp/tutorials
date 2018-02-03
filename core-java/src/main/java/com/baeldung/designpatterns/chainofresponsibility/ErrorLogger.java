package com.baeldung.designpatterns.chainofresponsibility;

public class ErrorLogger extends AbstractLogger {
    public ErrorLogger(int level) {
        this.level = level;
    }

    @Override
    protected String write(String message) {
        return "ERROR::Logger: " + message;
    }
}

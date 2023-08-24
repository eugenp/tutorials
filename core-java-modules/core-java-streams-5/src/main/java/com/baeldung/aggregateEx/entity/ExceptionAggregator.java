package com.baeldung.aggregateEx.entity;

import java.util.ArrayList;
import java.util.List;

public class ExceptionAggregator extends RuntimeException {
    private List<Exception> exceptions;

    public ExceptionAggregator(String message) {
        super(message);
        exceptions = new ArrayList<>();
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public Exception addException(Exception e) {
        this.addSuppressed(e);
        exceptions.add(e);
        return e;
    }

    public void addExceptions(List<Exception> exceptions) {
        exceptions.forEach(this::addException);
    }
}

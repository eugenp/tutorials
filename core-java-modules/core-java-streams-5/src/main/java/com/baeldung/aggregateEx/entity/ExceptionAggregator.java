package com.baeldung.aggregateEx.entity;

import java.util.ArrayList;
import java.util.List;

public class ExceptionAggregator extends RuntimeException {
    private List<Throwable> exceptions;

    public ExceptionAggregator(String message) {
        super(message);
        exceptions = new ArrayList<>();
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }

    public Throwable addException(Throwable e) {
        this.addSuppressed(e);
        exceptions.add(e);
        return e;
    }

    public void addExceptions(List<Throwable> exceptions) {
        exceptions.forEach(this::addException);
    }
}

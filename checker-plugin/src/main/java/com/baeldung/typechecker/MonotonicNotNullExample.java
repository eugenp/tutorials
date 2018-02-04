package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.util.Date;

public class MonotonicNotNullExample {

    // The idea is to lazily initialize that field,
    // so it starts as null but can only become not null.
    @MonotonicNonNull private Date firstCall;

    public Date getFirstCall() {
        if (firstCall == null) {
            firstCall = new Date();
        }
        return firstCall;
    }

    public void reset() {
        // This is reported as error because
        // we wrongly set it to null.
        firstCall = null;
    }

}

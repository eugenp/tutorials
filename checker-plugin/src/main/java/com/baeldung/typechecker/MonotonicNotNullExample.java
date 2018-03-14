package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.util.Date;

public class MonotonicNotNullExample {

    // The idea is we need this field to be to lazily initialized,
    // so it starts as null but once it becomes not null
    // it cannot return null.
    // In these cases, we can use @MonotonicNonNull
    @MonotonicNonNull private Date firstCall;

    public Date getFirstCall() {
        if (firstCall == null) {
            firstCall = new Date();
        }
        return firstCall;
    }

    public void reset() {
        // This is reported as error because
        // we wrongly set the field back to null.
        firstCall = null;
    }

}

package com.baeldung.exceptions;

public class YetAnotherException extends Exception {

    public YetAnotherException(String description) {
        super(description);
    }

    public YetAnotherException(String string, Throwable e) {
        super(string, e);
    }
}

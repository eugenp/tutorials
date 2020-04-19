package com.baeldung.exceptions.chainedexception.exceptions;

public class TeamLeadUpsetException extends Exception {

    public TeamLeadUpsetException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamLeadUpsetException(String message) {
        super(message);
    }
}

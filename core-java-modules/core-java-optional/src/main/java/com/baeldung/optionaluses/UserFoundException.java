package com.baeldung.optionaluses;

public class UserFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserFoundException(String message) {
        super(message);
    }

}

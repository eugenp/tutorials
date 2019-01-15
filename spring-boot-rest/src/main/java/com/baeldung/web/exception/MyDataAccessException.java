package com.baeldung.web.exception;

public final class MyDataAccessException extends RuntimeException {

    public MyDataAccessException() {
        super();
    }

    public MyDataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyDataAccessException(final String message) {
        super(message);
    }

    public MyDataAccessException(final Throwable cause) {
        super(cause);
    }

}

package com.baeldung.web.exception;

public final class MyDataIntegrityViolationException extends RuntimeException {

    public MyDataIntegrityViolationException() {
        super();
    }

    public MyDataIntegrityViolationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MyDataIntegrityViolationException(final String message) {
        super(message);
    }

    public MyDataIntegrityViolationException(final Throwable cause) {
        super(cause);
    }

}

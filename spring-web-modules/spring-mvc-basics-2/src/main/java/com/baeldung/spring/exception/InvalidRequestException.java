package com.baeldung.spring.exception;

public class InvalidRequestException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 4088649120307193208L;

    public InvalidRequestException() {
        super();
    }

    public InvalidRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestException(final String message) {
        super(message);
    }

    public InvalidRequestException(final Throwable cause) {
        super(cause);
    }

}

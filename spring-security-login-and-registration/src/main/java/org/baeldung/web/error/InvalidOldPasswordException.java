package org.baeldung.web.error;

public final class InvalidOldPasswordException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public InvalidOldPasswordException() {
        super();
    }

    public InvalidOldPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidOldPasswordException(final String message) {
        super(message);
    }

    public InvalidOldPasswordException(final Throwable cause) {
        super(cause);
    }

}

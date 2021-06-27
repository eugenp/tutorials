package com.baeldung.hexagonal.core.exception;

public class RepositoryException extends RuntimeException {
    private static final long serialVersionUID = -4162161362293289025L;

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }
}

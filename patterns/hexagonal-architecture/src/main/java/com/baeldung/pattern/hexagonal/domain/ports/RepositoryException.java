package com.baeldung.pattern.hexagonal.domain.ports;

import lombok.NoArgsConstructor;

/**
 * This exception is thrown by repositories when retrieving data occured an error.
 */
@NoArgsConstructor
public class RepositoryException extends Exception {

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

package com.baeldung.pattern.hexagonal.domain.ports;

import lombok.NoArgsConstructor;

/**
 * This exception is thrown by services.
 */
@NoArgsConstructor
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}

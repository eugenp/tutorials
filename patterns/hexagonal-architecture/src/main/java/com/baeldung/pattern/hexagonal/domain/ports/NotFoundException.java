package com.baeldung.pattern.hexagonal.domain.ports;

import lombok.NoArgsConstructor;

/**
 * This exception is thrown by services when a model could not be found.
 */
@NoArgsConstructor
public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

}

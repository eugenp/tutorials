package com.baeldung.hexagonalarchitecture.error;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 15:13
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}

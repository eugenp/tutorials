package com.baeldung.hexagonalarchitecture.error;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 15:13
 */
public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super(message);
    }
}

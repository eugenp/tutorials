package com.baeldung.exceptions.throwvsthrows;

public class NullOrEmptyException  extends RuntimeException {
    
    public NullOrEmptyException(String errorMessage) {
        super(errorMessage);
    }
}

package com.baeldung.junit5vstestng;

public class DivideByZeroException extends RuntimeException {

    public DivideByZeroException(String message) {
        super(message);
    }

}

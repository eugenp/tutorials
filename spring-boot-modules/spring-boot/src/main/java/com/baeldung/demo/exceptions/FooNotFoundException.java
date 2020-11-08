package com.baeldung.demo.exceptions;

public class FooNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 9042200028456133589L;

    public FooNotFoundException(String message) {
        super(message);
    }
}

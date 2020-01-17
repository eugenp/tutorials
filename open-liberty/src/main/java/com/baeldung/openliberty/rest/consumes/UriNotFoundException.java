package com.baeldung.openliberty.rest.consumes;

public class UriNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UriNotFoundException() {
        super();
    }

    public UriNotFoundException(String message) {
        super(message);
    }
}

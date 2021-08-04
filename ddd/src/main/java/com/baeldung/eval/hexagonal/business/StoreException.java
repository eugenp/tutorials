package com.baeldung.eval.hexagonal.business;

public class StoreException extends Exception {
    public StoreException(Exception ex) {
        super(ex);
    }

    public StoreException(String message) {
        super(message);
    }
}

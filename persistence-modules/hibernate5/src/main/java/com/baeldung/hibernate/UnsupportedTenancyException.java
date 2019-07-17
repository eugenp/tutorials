package com.baeldung.hibernate;

public class UnsupportedTenancyException extends Exception {
    public UnsupportedTenancyException (String message) {
        super(message);
    }

}

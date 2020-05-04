package com.baeldung.hexagonal.banking.application.service;

public class InvalidPinException extends RuntimeException {

    public InvalidPinException() {
        super("Invalid pin");
    }

}

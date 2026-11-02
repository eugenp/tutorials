package com.baeldung.springai.service;

public class BookingNotApprovedException extends RuntimeException {

    public BookingNotApprovedException(String message) {
        super(message);
    }
}

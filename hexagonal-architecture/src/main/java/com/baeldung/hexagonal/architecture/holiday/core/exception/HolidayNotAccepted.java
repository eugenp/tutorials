package com.baeldung.hexagonal.architecture.holiday.core.exception;

public class HolidayNotAccepted extends RuntimeException {

    public HolidayNotAccepted(String message) {
        super(message);
    }
}

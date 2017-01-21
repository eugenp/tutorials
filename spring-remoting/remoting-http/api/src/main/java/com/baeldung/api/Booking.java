package com.baeldung.api;

import java.io.Serializable;

import static java.lang.String.format;

public class Booking implements Serializable {

    private String bookingCode;

    public Booking(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    @Override public String toString() {
        return format("Ride confirmed: code '%s'.", bookingCode);
    }

}

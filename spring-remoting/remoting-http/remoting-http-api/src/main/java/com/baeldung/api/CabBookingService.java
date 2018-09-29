package com.baeldung.api;

public interface CabBookingService {
    Booking bookRide(String pickUpLocation) throws BookingException;
}

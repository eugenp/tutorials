package com.baeldung.api;

public interface CabBookingService {
    Booking bookPickUp(String pickUpLocation) throws BookingException;
}

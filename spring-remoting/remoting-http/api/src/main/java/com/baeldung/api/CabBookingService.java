package com.baeldung.api;

public interface CabBookingService {
    Booking bookPickUp(Address pickUpLocation, Address dropOffLocation, int pax) throws BookingException;
}

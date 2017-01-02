package com.baeldung.api;

public interface CabBookingServer {
    Booking bookPickUp(Address pickUp, Address dropDown, int people) throws BookingException;
}

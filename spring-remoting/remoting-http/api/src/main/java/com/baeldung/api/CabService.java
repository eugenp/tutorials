package com.baeldung.api;

public interface CabService {
    Booking bookPickUp(Address pickUp, Address dropDown, int people) throws BookingException;
}

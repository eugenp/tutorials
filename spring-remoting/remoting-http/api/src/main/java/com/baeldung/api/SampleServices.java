package com.baeldung.api;

public interface SampleServices {

    Booking bookPickUp(Address pickUp, Address dropDown, int people) throws BookinkException;
}

package com.baeldung.server;

import com.baeldung.api.*;

import java.util.Date;
import java.util.UUID;

public class CabServiceImpl implements CabBookingServer {

    @Override public Booking bookPickUp(Address pickUp, Address dropDown, int people) throws BookingException {

        if(Math.random()<0.3){
            throw new BookingException("Cab unavailable");
        }

        int tripTimeInMinutes = (int) (5 + Math.random() * 15);
        int costInCent = 15_00 + tripTimeInMinutes * 5 * people;
        Date pickUpDate = new Date((long) (System.currentTimeMillis() + (1000 * 60 * Math.random() * 15)));
        return new Booking(
                pickUp, pickUpDate,
                dropDown, costInCent, tripTimeInMinutes,
                UUID.randomUUID().toString());
    }
}

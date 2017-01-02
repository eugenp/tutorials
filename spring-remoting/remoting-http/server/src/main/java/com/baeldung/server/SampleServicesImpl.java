package com.baeldung.server;

import com.baeldung.api.*;

import java.util.Date;
import java.util.UUID;

public class SampleServicesImpl implements SampleServices {

    @Override public Booking bookPickUp(Address pickUp, Address dropDown, int people) throws BookinkException {

        if(Math.random()<0.3){
            throw new BookinkException("Cab unavailable");
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

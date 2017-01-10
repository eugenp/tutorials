package com.baeldung.server;

import com.baeldung.api.Address;
import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;

import java.util.Date;

import static java.lang.Math.random;
import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;

public class CabBookingServiceImpl implements CabBookingService {

    @Override public Booking bookPickUp(Address pickUpLocation, Address dropOffLocation, int pax) throws BookingException {
        if(random()<0.3){
            throw new BookingException("Cab unavailable");
        }
        int tripTimeInMinutes = (int) (5 + random() * 15);
        int costInCent = 15_00 + tripTimeInMinutes * 5 * pax;
        Date pickUpDate = new Date((long) (currentTimeMillis() + (1000 * 60 * random() * 15)));
        return new Booking(pickUpLocation, pickUpDate, dropOffLocation, costInCent, tripTimeInMinutes * 60,
                randomUUID().toString());
    }
}

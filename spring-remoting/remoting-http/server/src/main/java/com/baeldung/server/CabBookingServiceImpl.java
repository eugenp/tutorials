package com.baeldung.server;

import com.baeldung.api.Address;
import com.baeldung.api.Booking;
import com.baeldung.api.BookingException;
import com.baeldung.api.CabBookingService;

import java.time.LocalDateTime;

import static java.lang.Math.random;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.UUID.randomUUID;

public class CabBookingServiceImpl implements CabBookingService {

    @Override public Booking bookPickUp(Address pickUpLocation, Address dropOffLocation, int pax) throws BookingException {
        if (random() < 0.3) {
            throw new BookingException("Cab unavailable");
        }
        int tripTimeInMinutes = (int) (5 + random() * 15);
        int costInCent = 15_00 + tripTimeInMinutes * 5 * pax;
        LocalDateTime pickUpDate = now().plus(15L * (long) (random() * 10), MINUTES);

        return new Booking(pickUpLocation, pickUpDate, dropOffLocation, costInCent, tripTimeInMinutes * 60, randomUUID().toString());
    }
}

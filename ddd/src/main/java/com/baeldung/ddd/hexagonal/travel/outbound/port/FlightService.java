package com.baeldung.ddd.hexagonal.travel.outbound.port;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.Reservation;

public interface FlightService {
    Collection<Reservation> getBookings();

    Reservation reserveTicket(String source, String destination, List<Passenger> passengers, ZonedDateTime startTime);
    
    void cancelReservation(String ticketId);
}

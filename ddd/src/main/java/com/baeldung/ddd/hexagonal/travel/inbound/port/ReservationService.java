package com.baeldung.ddd.hexagonal.travel.inbound.port;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.ServiceType;
import com.baeldung.ddd.hexagonal.travel.domain.Reservation;

public interface ReservationService {

    String reserveTicket(String source, String destination, ServiceType type, List<Passenger> passengers, ZonedDateTime startTime);

    Collection<Reservation> getAllReservations();

    void cancelReservation(String ticketId);
}

package com.baeldung.ddd.hexagonal.travel.outbound.adapter;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.ddd.hexagonal.travel.cache.ReservationCache;
import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.Reservation;
import com.baeldung.ddd.hexagonal.travel.domain.ServiceType;
import com.baeldung.ddd.hexagonal.travel.outbound.port.BusService;

@Service
public class MegaBusServiceImpl implements BusService {

    @Override
    public Reservation reserveTicket(String source, String destination, List<Passenger> passengers, ZonedDateTime startTime) {
        // Talks to external Bus providers and retrieves the tickets.
        Reservation ticket = reservationRequestToExternalSystem(source, destination, passengers, startTime);
        // Once booking confirmed caches the ticket
        ReservationCache.put(ticket.getId(), ticket);
        return ReservationCache.get(ticket.getId());
    }

    @Override
    public void cancelReservation(String ticketId) {
        // Talks to external Bus provider and cancels the ticket.
        Reservation cancelledTicketDetails = cancelRequestToExternalSystem(ticketId);
        // Once cancelled the details are updated in local cache.
        ReservationCache.put(ticketId, cancelledTicketDetails);
    }

    @Override
    public Collection<Reservation> getBookings() {
        return ReservationCache.getAllReservations();
    }

    private Reservation reservationRequestToExternalSystem(String source, String destination, List<Passenger> passengers, ZonedDateTime startTime) {
        // Dummy implementation for external system
        return new Reservation(source, destination, ServiceType.BUS, passengers, startTime);
    }

    private Reservation cancelRequestToExternalSystem(String ticketId) {
        // Dummy implementation for external system
        Reservation ticket = ReservationCache.get(ticketId);
        ticket.setStatus("Cancelled");
        return ticket;
    }
}

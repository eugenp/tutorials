package com.baeldung.ddd.hexagonal.travel.inbound.adapter;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.ddd.hexagonal.travel.cache.ReservationCache;
import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.ServiceType;
import com.baeldung.ddd.hexagonal.travel.domain.Reservation;
import com.baeldung.ddd.hexagonal.travel.inbound.port.ReservationService;
import com.baeldung.ddd.hexagonal.travel.outbound.port.BusService;
import com.baeldung.ddd.hexagonal.travel.outbound.port.FlightService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private FlightService flightService;
    private BusService busService;

    @Autowired
    public ReservationServiceImpl(FlightService flightService, BusService busService) {
        this.flightService = flightService;
        this.busService = busService;
    }

    @Override
    public String reserveTicket(String source, String destination, ServiceType type, List<Passenger> passengers, ZonedDateTime travelDate) {
        final Reservation ticket;
        switch (type) {
        case FLIGHT:
            ticket = flightService.reserveTicket(source, destination, passengers, travelDate);
            break;
        case BUS:
            ticket = busService.reserveTicket(source, destination, passengers, travelDate);
            break;
        default:
            ticket = new Reservation(source, destination, ServiceType.INVALID, passengers, travelDate);
            break;

        }
        return ticket.getId();
    }

    @Override
    public Collection<Reservation> getAllReservations() {
        return ReservationCache.getAllReservations();
    }

    @Override
    public void cancelReservation(String ticketId) {
        ServiceType type = ReservationCache.get(ticketId)
            .getServiceType();
        switch (type) {
        case FLIGHT:
            flightService.cancelReservation(ticketId);
            break;
        case BUS:
            busService.cancelReservation(ticketId);
            break;
        default:
            break;
        }
    }

}

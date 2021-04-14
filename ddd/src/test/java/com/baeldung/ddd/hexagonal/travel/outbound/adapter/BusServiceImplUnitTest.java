package com.baeldung.ddd.hexagonal.travel.outbound.adapter;

import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.ddd.hexagonal.travel.cache.ReservationCache;
import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.Reservation;
import com.baeldung.ddd.hexagonal.travel.domain.ServiceType;

public class BusServiceImplUnitTest {

    private MegaBusServiceImpl busServiceImpl;
    ZonedDateTime startTime = ZonedDateTime.now();
    List<Passenger> passengers = Collections.singletonList(new Passenger("Sarath", "UAXSS", 24));
    private Reservation airReservation = new Reservation("NY", "OH", ServiceType.FLIGHT, passengers, startTime);
    private Reservation busReservation = new Reservation("NY", "OH", ServiceType.BUS, passengers, startTime);

    @BeforeEach
    void setUp() {
        ReservationCache.put(airReservation.getId(), airReservation);
        ReservationCache.put(busReservation.getId(), busReservation);
        busServiceImpl = new MegaBusServiceImpl();
    }

    @Test
    void reserve_bus_ticket() {
        Reservation reservation = busServiceImpl.reserveTicket("NY", "OH", passengers, startTime);
        assertTrue(ReservationCache.getAllReservations()
            .stream()
            .anyMatch(ticket -> ticket.getId()
                .equals(reservation.getId())));
    }

    @Test
    void cancel_bus_ticket() {
        busServiceImpl.cancelReservation(busReservation.getId());
        assertTrue(ReservationCache.getAllReservations()
            .stream()
            .filter(ticket -> ticket.getId()
                .equals(busReservation.getId()))
            .anyMatch(ticket -> ticket.getStatus()
                .equals("Cancelled")));
    }

}

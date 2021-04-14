package com.baeldung.ddd.hexagonal.travel.inbound.adapter;

import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.ddd.hexagonal.travel.cache.ReservationCache;
import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.Reservation;
import com.baeldung.ddd.hexagonal.travel.domain.ServiceType;
import com.baeldung.ddd.hexagonal.travel.outbound.port.BusService;
import com.baeldung.ddd.hexagonal.travel.outbound.port.FlightService;

public class ReservationServiceImplUnitTest {

    private FlightService flightService;
    private BusService busService;
    private ReservationServiceImpl reservationServiceImpl;

    ZonedDateTime startTime = ZonedDateTime.now();
    List<Passenger> passengers = Collections.singletonList(new Passenger("Sarath", "UAXSS", 24));
    private Reservation airReservation = new Reservation("NY", "OH", ServiceType.FLIGHT, passengers, startTime);
    private Reservation busReservation = new Reservation("NY", "OH", ServiceType.BUS, passengers, startTime);

    @BeforeEach
    void setUp() {
        flightService = Mockito.mock(FlightService.class);
        busService = Mockito.mock(BusService.class);
        ReservationCache.put(airReservation.getId(), airReservation);
        ReservationCache.put(busReservation.getId(), busReservation);
        reservationServiceImpl = new ReservationServiceImpl(flightService, busService);
    }

    @Test
    void reserve_flight_ticket() {
        Mockito.when(flightService.reserveTicket("NY", "OH", passengers, startTime))
            .thenReturn(airReservation);
        String id = reservationServiceImpl.reserveTicket("NY", "OH", ServiceType.FLIGHT, passengers, startTime);
        assertTrue(reservationServiceImpl.getAllReservations()
            .stream()
            .anyMatch(ticket -> ticket.getId()
                .equals(id)));
    }

    @Test
    void reserve_bus_ticket() {
        Mockito.when(busService.reserveTicket("NY", "OH", passengers, startTime))
            .thenReturn(busReservation);
        String id = reservationServiceImpl.reserveTicket("NY", "OH", ServiceType.BUS, passengers, startTime);
        assertTrue(reservationServiceImpl.getAllReservations()
            .stream()
            .anyMatch(ticket -> ticket.getId()
                .equals(id)));
    }

    @Test
    void cancel_flight_ticket() {
        reservationServiceImpl.cancelReservation(airReservation.getId());
        Mockito.verify(flightService)
            .cancelReservation(airReservation.getId());
    }

    @Test
    void cancel_bus_ticket() {
        reservationServiceImpl.cancelReservation(busReservation.getId());
        Mockito.verify(busService)
            .cancelReservation(busReservation.getId());
    }

}

package com.baeldung.core;

import com.baeldung.outbound.AirlineTicketsOutboundPort;

import java.time.LocalDate;
import java.util.List;

public class AirlineService {

    private AirlineTicketsOutboundPort airlineTicketsOutboundPort;

    public AirlineService(AirlineTicketsOutboundPort airlineTicketsOutboundPort) {
        this.airlineTicketsOutboundPort = airlineTicketsOutboundPort;
    }

    public List<AirlineTicket> search(LocalDate start, LocalDate end, String fromCity, String toCity) {
        // TODO sort tickets, create optimal itinerary
        return airlineTicketsOutboundPort.search(start, end, fromCity, toCity);
    }
}

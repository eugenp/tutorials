package com.baeldung.hexagonalarchitecture.domain;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class FlightsFinder implements IFlightsFinder {
    private FlightsProvider flightsProvider;

    public FlightsFinder(FlightsProvider flightsProvider) {
        this.flightsProvider = flightsProvider;
    }

    public Flight checkFlights(String origin, String destination) throws IOException {
        List<Flight> flights = this.flightsProvider.findFlights(origin, destination);
        return flights.stream()
            .min(Comparator.comparing(Flight::getPrice))
            .orElseThrow(NoSuchElementException::new);
    }
}

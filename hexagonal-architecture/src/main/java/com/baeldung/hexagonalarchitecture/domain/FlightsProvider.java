package com.baeldung.hexagonalarchitecture.domain;

import java.io.IOException;
import java.util.List;

public interface FlightsProvider {
    List<Flight> findFlights(String origin, String destination) throws IOException;
}

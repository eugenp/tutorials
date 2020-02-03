package com.baeldung.hexagonalarchitecture.domain;

import java.io.IOException;

public interface IFlightsFinder {
    Flight checkFlights(String origin, String destination) throws IOException;
}

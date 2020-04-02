package com.baeldung.springboot.hexagonal.infrastructure;

import java.util.List;

import com.baeldung.springboot.hexagonal.domain.Flight;

public interface FlightService {
    public List<Flight> getRoute(String date, String origin, String destination);
}

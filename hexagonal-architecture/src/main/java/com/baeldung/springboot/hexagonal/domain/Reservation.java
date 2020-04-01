package com.baeldung.springboot.hexagonal.domain;

import java.util.List;
import java.util.UUID;

public class Reservation {
    /**
     * Functional ID of the reservation
     */
    private UUID id;
    /**
     * Customer holding the reservation
     */
    private Long customerId;
    /**
     * List of flights in the reservation
     */
    private List<Flight> flights;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}

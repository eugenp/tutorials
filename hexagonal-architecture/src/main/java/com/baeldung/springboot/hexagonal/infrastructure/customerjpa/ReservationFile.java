package com.baeldung.springboot.hexagonal.infrastructure.customerjpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ReservationFile {
    @Id
    @GeneratedValue
    private Long id;
    private UUID reservationId;
    private Long customerId;
    private String travelDate;
    private String flightNumbers;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<FlightEntity> flights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public void setReservationId(UUID reservationId) {
        this.reservationId = reservationId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getFlightNumbers() {
        return flightNumbers;
    }

    public void setFlightNumbers(String flightNumbers) {
        this.flightNumbers = flightNumbers;
    }

    public List<FlightEntity> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightEntity> flights) {
        this.flights = flights;
    }
}

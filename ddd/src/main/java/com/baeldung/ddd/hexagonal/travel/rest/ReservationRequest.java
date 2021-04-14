package com.baeldung.ddd.hexagonal.travel.rest;

import java.time.ZonedDateTime;
import java.util.List;

import com.baeldung.ddd.hexagonal.travel.domain.Passenger;
import com.baeldung.ddd.hexagonal.travel.domain.ServiceType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationRequest {

    @JsonProperty("source")
    private String source;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("service_type")
    private ServiceType serviceType;

    @JsonProperty("passengers")
    private List<Passenger> passengers;

    @JsonProperty("start_time")
    private ZonedDateTime startTime;

    @JsonCreator
    public ReservationRequest(String source, String destination, ServiceType serviceType, ZonedDateTime startTime, List<Passenger> passengers) {
        this.source = source;
        this.destination = destination;
        this.serviceType = serviceType;
        this.passengers = passengers;
        this.startTime = startTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

}

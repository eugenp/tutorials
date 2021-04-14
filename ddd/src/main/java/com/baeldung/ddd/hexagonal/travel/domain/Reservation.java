package com.baeldung.ddd.hexagonal.travel.domain;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Reservation {

    private String id;
    private List<Passenger> passengers;
    private ServiceType serviceType;
    private String source;
    private String destination;
    private ZonedDateTime startTime;
    private String status;

    public Reservation(String source, String destination, ServiceType type, List<Passenger> passengers, ZonedDateTime startTime) {
        this.id = UUID.randomUUID()
            .toString();
        this.source = source;
        this.destination = destination;
        this.serviceType = type;
        this.passengers = passengers;
        this.startTime = startTime;
        this.status = "Confirmed";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengerName(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
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

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

package com.baeldung.dapr.pubsub.model;

public class RideRequest {
    private String passengerId;
    private String location;
    private String destination;

    public RideRequest() {
    }

    public RideRequest(String passengerId, String location, String destination) {
        this.passengerId = passengerId;
        this.location = location;
        this.destination = destination;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "RideRequest [passengerId=" + passengerId + ", location=" + location + ", destination=" + destination
                + "]";
    }
}

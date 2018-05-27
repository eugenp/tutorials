package com.baeldung.reactive.alex.comsa;

public class AirPlane {

    private String flightNumber;
    private String destination;
    private String airplaneType;

    public AirPlane(String flightNumber, String destination, String airplaneType) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.airplaneType = airplaneType;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(String airplaneType) {
        this.airplaneType = airplaneType;
    }
}

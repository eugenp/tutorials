package com.baeldung.springboot.hexagonal.domain;

public class Flight {
    private String number;
    private String origin;
    private String destination;
    private String date;

    public Flight() {
    }

    public Flight(String number, String origin, String destination, String date) {
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.baeldung.springboot.hexagonal.infrastructure.customerjpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FlightEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String number;
    private String origin;
    private String destination;
    private String date;

    public FlightEntity() {
    }

    public FlightEntity(Long id, String number, String origin, String destination, String date) {
        this.id = id;
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

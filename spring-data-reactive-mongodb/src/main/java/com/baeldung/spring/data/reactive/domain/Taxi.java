package com.baeldung.spring.data.reactive.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by shazi on 11/20/2017.
 */
@Document
public class Taxi {

    @Id
    private String id;

    private String number;

    private int noOfSeats;

    public Taxi(String id, String number, int noOfSeats) {
        this.id = id;
        this.number = number;
        this.noOfSeats = noOfSeats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Taxi taxi = (Taxi) o;

        if (noOfSeats != taxi.noOfSeats) return false;
        if (id != null ? !id.equals(taxi.id) : taxi.id != null) return false;
        return number != null ? number.equals(taxi.number) : taxi.number == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + noOfSeats;
        return result;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", noOfSeats=" + noOfSeats +
                '}';
    }
}

package com.baeldung.core;

import java.time.LocalDateTime;

public class AirlineTicket {

    private LocalDateTime date;
    private int cost;
    private String fromCity;
    private String toCity;

    public AirlineTicket(LocalDateTime date, int cost, String fromCity, String toCity) {
        this.date = date;
        this.cost = cost;
        this.fromCity = fromCity;
        this.toCity = toCity;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}

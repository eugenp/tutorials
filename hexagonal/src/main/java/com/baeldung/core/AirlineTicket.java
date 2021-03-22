package com.baeldung.core;

import java.time.LocalDateTime;

public class AirlineTicket {

    private LocalDateTime date;
    private int cost;
    private String fromCity;
    private String toCity;

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

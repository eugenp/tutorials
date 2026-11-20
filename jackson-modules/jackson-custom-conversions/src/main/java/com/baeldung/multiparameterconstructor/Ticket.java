package com.baeldung.multiparameterconstructor;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {

    @JsonProperty(value = "event")
    private String eventName;

    private String guest;

    private final Currency currency;

    private int price;

    public Ticket(String eventName, String guest, Currency currency, int price) {
        this.eventName = eventName;
        this.guest = guest;
        this.currency = currency;
        this.price = price;
    }

    @JsonCreator
    public Ticket(Currency currency, int price) {
        this.price = price;
        this.currency = currency;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getGuest() {
        return guest;
    }

    public String getEventName() {
        return eventName;
    }

    public Currency getCurrency() {
        return currency;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ticket ticket = (Ticket) o;
        return price == ticket.price && Objects.equals(eventName, ticket.eventName) && Objects.equals(guest, ticket.guest) && currency == ticket.currency;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(eventName);
        result = 31 * result + Objects.hashCode(guest);
        result = 31 * result + Objects.hashCode(currency);
        result = 31 * result + price;
        return result;
    }
}

package com.baeldung.rate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Quote {
    private String currency;
    private BigDecimal ask;
    private BigDecimal bid;
    private LocalDate date;

    public Quote(String currency, BigDecimal ask, BigDecimal bid) {
        this.currency = currency;
        this.ask = ask;
        this.bid = bid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
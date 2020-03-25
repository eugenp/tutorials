package com.baeldung.hexagonalarchitecture.corejava.domain;

import java.util.Date;

public class ExchangeRate {

    private String currencyCode;
    private Date date;
    private double rate;

    public ExchangeRate(String currencyCode, Date date, double rate) {
        this.currencyCode = currencyCode;
        this.date = date;
        this.rate = rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

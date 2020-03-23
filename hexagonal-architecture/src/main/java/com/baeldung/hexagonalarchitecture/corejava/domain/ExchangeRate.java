package com.baeldung.hexagonalarchitecture.corejava.domain;

import java.util.Date;

public class ExchangeRate {

    private String currencyCode;
    private Date date;
    private double exchangeRate;

    public ExchangeRate(String currencyCode, Date date, double exchangeRate) {
        this.currencyCode = currencyCode;
        this.date = date;
        this.exchangeRate = exchangeRate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

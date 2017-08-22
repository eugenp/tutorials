package com.baeldung.beaninjection.model;

public class JapaneseYen implements Currency {
    public double getUSDExchangeRate() {
        return 123.45;
    }

    public String getCode() {
        return "JPY";
    }
}

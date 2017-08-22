package com.baeldung.beaninjection.model;

public class IndianRupee implements Currency {
    public double getUSDExchangeRate() {
        return 678.90;
    }

    public String getCode() {
        return "INR";
    }
}

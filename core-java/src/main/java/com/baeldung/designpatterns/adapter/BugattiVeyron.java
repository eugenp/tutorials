package com.baeldung.designpatterns.adapter;

public class BugattiVeyron implements LuxuryCars {
    @Override
    public double speedInMPH() {
        return 268;
    }
}

package com.baeldung.designpatterns.adapter;

public class McLaren implements LuxuryCars {
    @Override
    public double speedInMPH() {
        return 241;
    }
}

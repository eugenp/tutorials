package com.baeldung.designpatterns.adapter;

public class AstonMartin implements LuxuryCars {
    @Override
    public double speedInMPH() {
        return 220;
    }
}

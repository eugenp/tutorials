package com.baeldung.designpatterns.adapter;

public class AstonMartin implements Movable {
    @Override
    public double getSpeed() {
        return 220;
    }
}

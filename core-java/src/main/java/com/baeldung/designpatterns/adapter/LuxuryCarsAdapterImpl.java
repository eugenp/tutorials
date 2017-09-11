package com.baeldung.designpatterns.adapter;

public class LuxuryCarsAdapterImpl implements LuxuryCarsAdapter {
    private Movable luxuryCars;
    
    public LuxuryCarsAdapterImpl(Movable luxuryCars) {
        this.luxuryCars = luxuryCars;
    }

    @Override
    public double getSpeed() {
        double mph = luxuryCars.getSpeed();
        return convertMPHtoKMPH(mph);
    }
    
    private double convertMPHtoKMPH(double mph) {
        return mph * 1.60934;
    }
}

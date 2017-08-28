package com.baeldung.designpatterns.adapter;

public class LuxuryCarsAdapterImpl implements LuxuryCarsAdapter {
    private LuxuryCars luxuryCarsSpeed;
    
    public LuxuryCarsAdapterImpl(LuxuryCars luxuryCarsSpeed) {
        this.luxuryCarsSpeed = luxuryCarsSpeed;
    }

    @Override
    public double speedInKMPH() {
        double mph = luxuryCarsSpeed.speedInMPH();
        return convertMPHtoKMPH(mph);
    }
    
    private double convertMPHtoKMPH(double mph) {
        return mph * 1.60934;
    }
}

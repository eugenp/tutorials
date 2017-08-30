package com.baeldung.designpatterns.adapter;

public class LuxuryCarsAdapterImpl implements LuxuryCarsAdapter {
    private LuxuryCars luxuryCars;
    
    public LuxuryCarsAdapterImpl(LuxuryCars luxuryCars) {
        this.luxuryCars = luxuryCars;
    }

    @Override
    public double speedInKMPH() {
        double mph = luxuryCars.speedInMPH();
        return convertMPHtoKMPH(mph);
    }
    
    private double convertMPHtoKMPH(double mph) {
        return mph * 1.60934;
    }
}

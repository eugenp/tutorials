package com.baeldung.designpatterns.adapter;

public class LuxuryCarsSpeedAdapterImpl extends LuxuryCarsSpeed implements LuxuryCarsSpeedAdapter {

    @Override
    public double bugattiVeyronInKMPH() {
        double mph = super.bugattiVeyronInMPH();
        return convertMPHtoKMPH(mph);
    }

    @Override
    public double mcLarenInKMPH() {
        double mph = super.mcLarenInMPH();
        return convertMPHtoKMPH(mph);
    }

    @Override
    public double astonMartinInKMPH() {
        double mph = super.astonMartinInMPH();
        return convertMPHtoKMPH(mph);
    }

    private double convertMPHtoKMPH(double mph) {
        return mph * 1.60934;
    }
}

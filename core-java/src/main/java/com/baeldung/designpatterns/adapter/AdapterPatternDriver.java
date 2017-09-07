package com.baeldung.designpatterns.adapter;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class AdapterPatternDriver {
    
    public static void main(String args[]) {
        LuxuryCars bugattiVeyron = new BugattiVeyron();
        LuxuryCarsAdapter bugattiVeyronAdapter = new LuxuryCarsAdapterImpl(bugattiVeyron);
        LOG.info("Bugatti Veyron Super Sport's top speed is " + bugattiVeyronAdapter.speedInKMPH() + " Kmph.");

        LuxuryCars mcLaren = new McLaren();
        LuxuryCarsAdapter mcLarenAdapter = new LuxuryCarsAdapterImpl(mcLaren);
        LOG.info("McLaren F1 top speed is " + mcLarenAdapter.speedInKMPH() + " Kmph.");

        LuxuryCars astonMartin = new AstonMartin();
        LuxuryCarsAdapter astonMartinAdapter = new LuxuryCarsAdapterImpl(astonMartin);
        LOG.info("McLaren F1 top speed is " + astonMartinAdapter.speedInKMPH() + " Kmph.");
    }
}

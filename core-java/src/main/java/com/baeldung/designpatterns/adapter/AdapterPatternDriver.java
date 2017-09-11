package com.baeldung.designpatterns.adapter;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class AdapterPatternDriver {
    
    public static void main(String args[]) {
    	Movable bugattiVeyron = new BugattiVeyron();
        LuxuryCarsAdapter bugattiVeyronAdapter = new LuxuryCarsAdapterImpl(bugattiVeyron);
        LOG.info("Bugatti Veyron Super Sport's top speed is " + bugattiVeyronAdapter.getSpeed() + " Kmph.");

        Movable mcLaren = new McLaren();
        LuxuryCarsAdapter mcLarenAdapter = new LuxuryCarsAdapterImpl(mcLaren);
        LOG.info("McLaren F1 top speed is " + mcLarenAdapter.getSpeed() + " Kmph.");

        Movable astonMartin = new AstonMartin();
        LuxuryCarsAdapter astonMartinAdapter = new LuxuryCarsAdapterImpl(astonMartin);
        LOG.info("McLaren F1 top speed is " + astonMartinAdapter.getSpeed() + " Kmph.");
    }
}

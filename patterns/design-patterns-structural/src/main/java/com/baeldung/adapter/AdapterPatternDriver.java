package com.baeldung.adapter;

import static com.baeldung.util.LoggerUtil.LOG;

public class AdapterPatternDriver {
    
    public static void main(String args[]) {
    	Movable bugattiVeyron = new BugattiVeyron();
    	MovableAdapter bugattiVeyronAdapter = new MovableAdapterImpl(bugattiVeyron);
        LOG.info("Bugatti Veyron Super Sport's top speed is " + bugattiVeyronAdapter.getSpeed() + " Kmph.");

        Movable mcLaren = new McLaren();
        MovableAdapter mcLarenAdapter = new MovableAdapterImpl(mcLaren);
        LOG.info("McLaren F1 top speed is " + mcLarenAdapter.getSpeed() + " Kmph.");

        Movable astonMartin = new AstonMartin();
        MovableAdapter astonMartinAdapter = new MovableAdapterImpl(astonMartin);
        LOG.info("McLaren F1 top speed is " + astonMartinAdapter.getSpeed() + " Kmph.");
    }
}

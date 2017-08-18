package com.baeldung.designpatterns.adapter;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class AdapterPatternDriver {
    
    public static void main(String args[]) {
        LuxuryCarsSpeedAdapter luxuryCars = new LuxuryCarsSpeedAdapterImpl();
        LOG.info("Bugatti Veyron Super Sport's top speed is " + luxuryCars.bugattiVeyronInKMPH() + " Kmph.");
        LOG.info("McLaren F1 top speed is " + luxuryCars.mcLarenInKMPH() + " Kmph.");
        LOG.info("Aston Martin One-77 top speed is " + luxuryCars.astonMartinInKMPH() + " Kmph.");
    }
}

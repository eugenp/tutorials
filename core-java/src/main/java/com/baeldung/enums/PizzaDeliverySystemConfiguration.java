package com.baeldung.enums;

/**
 * Created by D-YW44CN on 2/05/2016.
 */
public enum PizzaDeliverySystemConfiguration {
    INSTANCE ;
    private PizzaDeliverySystemConfiguration() {
        //Do the configuration initialization which
        // involves overriding defaults like delivery strategy
    }

    //default delivery strategy
    private PizzaDeliveryStrategy deliveryStrategy = PizzaDeliveryStrategy.NORMAL;

    public static PizzaDeliverySystemConfiguration getInstance() {
        return INSTANCE;
    }

    public PizzaDeliveryStrategy getDeliveryStrategy() {
        return deliveryStrategy;
    }

}

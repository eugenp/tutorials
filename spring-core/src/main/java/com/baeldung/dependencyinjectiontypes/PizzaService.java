package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaService {

    private final PizzaMaker pizzaMaker;

    private final PizzaDeliveryGuy deliveryGuy;

    @Autowired
    OrderingService orderingService;

    @Autowired
    public PizzaService(PizzaMaker pizzaMaker, PizzaDeliveryGuy deliveryGuy) {
        this.pizzaMaker = pizzaMaker;
        this.deliveryGuy = deliveryGuy;
    }
}

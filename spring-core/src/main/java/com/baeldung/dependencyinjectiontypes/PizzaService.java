package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PizzaService {

    private final PizzaMaker pizzaMaker;

    private final PizzaDeliveryGuy deliveryGuy;

    private PizzaPineappler pizzaPineappler;

    @Autowired
    public PizzaService(PizzaMaker pizzaMaker, PizzaDeliveryGuy deliveryGuy) {
        this.pizzaMaker = pizzaMaker;
        this.deliveryGuy = deliveryGuy;
    }

    @Autowired
    public void setPizzaPineappler(PizzaPineappler pizzaPineappler) {
        this.pizzaPineappler = pizzaPineappler;
    }

    public void orderPizza() {
        // pretend doing something
    }

    public static void main(String[] args) {
        System.out.println("test");
        ConfigurableApplicationContext appContext = new AnnotationConfigApplicationContext("com.baeldung.dependencyinjectiontypes");
        PizzaService ps = appContext.getBean(PizzaService.class);
    }
}

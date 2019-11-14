package com.baeldung.mockito.fluentapi;



import org.junit.Test;

import com.baeldung.mockito.fluentapi.Pizza.PizzaSize;

public class PizzaUnitTest {

    @Test
    public void givenPizza_whenBuilt_thenShouldReturnPizzaWithCorrectAttributes() {
        Pizza pizza = new Pizza
            .PizzaBuilder("Margherita")
            .size(PizzaSize.LARGE)
            .withExtaTopping("Mushroom")
            .withStuffedCrust(false)
            .willCollect(true)
            .applyDiscount(20)
            .build();
        
        //assert
    }
    
    @Test
    public void givenPizza_whenBuiltWithTraditonalMock_thenShouldReturnPizza() {
        
        Mock
        
        
        Pizza pizza = new Pizza
            .PizzaBuilder("Margherita")
            .size(PizzaSize.LARGE)
            .withExtaTopping("Mushroom")
            .withStuffedCrust(false)
            .willCollect(true)
            .applyDiscount(20)
            .build();
        
        //assert
    }

}

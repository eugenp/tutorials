package com.baeldung.dependencyinjectiontypes;

import org.junit.Test;
import org.mockito.Mockito;

public class PizzaServiceTest {

    @Test
    public void testPizzaService() {
        // Arrange
        PizzaService mockPizzaService = new PizzaService(Mockito.mock(PizzaMaker.class), Mockito.mock(PizzaDeliveryGuy.class));
            
        
        
    }
}

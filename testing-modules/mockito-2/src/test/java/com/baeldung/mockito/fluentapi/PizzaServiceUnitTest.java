package com.baeldung.mockito.fluentapi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

import com.baeldung.mockito.fluentapi.Pizza.PizzaSize;

public class PizzaServiceUnitTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ArrayList<String> mockList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    public void givenPizzaWithToppings_whenServiceInvoked_thenReturnsList() {

        //Foo mock = mock(Foo.class, RETURNS_DEEP_STUBS);
        
        List<String> bob = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        
        
        when(mockList.stream()
            .filter(s -> s.startsWith("c"))
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList()))
        .thenReturn(bob);
        
       Assert.assertEquals(bob, mockList.stream()
            .filter(s -> s.startsWith("c"))
            .map(String::toUpperCase)
            .sorted()
            .collect(Collectors.toList()));
        
        
//        List<String> filtered = Arrays.asList("a1", "a2", "b1", "c2", "c1")
//            .stream()
//            .filter(s -> s.startsWith("c"))
//            .map(String::toUpperCase)
//            .sorted()
//            .collect(Collectors.toList());
//        
//        
//        
//        
//        Pizza.PizzaBuilder builder = Mockito.mock(Pizza.PizzaBuilder.class, Mockito.RETURNS_DEEP_STUBS);
//        
//        Pizza pizza = Mockito.mock(Pizza.class);
//        
//        Mockito.when(builder
//          .size(PizzaSize.LARGE)
//          .withExtaTopping("Mushroom")
//          .withStuffedCrust(false)
//          .willCollect(true)
//          .applyDiscount(20)
//          .build()).thenReturn(pizza);
//        
//        PizzaService service = new PizzaService(builder);
//        
//        List<String> listToppings = service.listToppings();
        
        
    }

}

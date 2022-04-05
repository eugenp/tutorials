package com.baeldung.junit5.order;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(CustomOrder.class)
public class CustomOrderUnitTest {
    private static StringBuilder output = new StringBuilder("");
    
    @Test
    public void myATest() {
        output.append("A");
    }
    
    @Test
    public void myBTest() {
        output.append("B");        
    }
    
    @Test
    public void myaTest() {
        output.append("a");
    }

 
    @AfterAll
    public static void assertOutput() {
        assertEquals(output.toString(), "AaB");
    }
}

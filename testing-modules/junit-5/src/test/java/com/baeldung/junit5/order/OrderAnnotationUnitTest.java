package com.baeldung.junit5.order;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class OrderAnnotationUnitTest {
    private static StringBuilder output = new StringBuilder("");
    
    @Test
    @Order(1)    
    public void firstTest() {
        output.append("a");
    }
    
    @Test
    @Order(2)    
    public void secondTest() {
        output.append("b");
    }
 
    @Test
    @Order(3)    
    public void thirdTest() {
        output.append("c");
    }
 
    @AfterAll
    public static void assertOutput() {
        assertEquals(output.toString(), "abc");
    }
}

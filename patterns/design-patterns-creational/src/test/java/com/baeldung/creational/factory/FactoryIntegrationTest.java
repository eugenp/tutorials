package com.baeldung.creational.factory;

import static org.junit.Assert.*;

import org.junit.Test;

public class FactoryIntegrationTest {

    @Test
    public void whenUsingFactoryForSquare_thenCorrectObjectReturned() {
        Polygon p;
        PolygonFactory factory = new PolygonFactory();
        
        //get the shape which has 4 sides
        p = factory.getPolygon(4);
        String result = "The shape with 4 sides is a " + p.getType();
        
        assertEquals("The shape with 4 sides is a Square", result);
    }
    
    @Test
    public void whenUsingFactoryForOctagon_thenCorrectObjectReturned() {
        Polygon p;
        PolygonFactory factory = new PolygonFactory();
        
        //get the shape which has 4 sides
        p = factory.getPolygon(8);
        String result = "The shape with 8 sides is a " + p.getType();
        
        assertEquals("The shape with 8 sides is a Octagon", result);
    }
}

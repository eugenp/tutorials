package com.baeldung;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.bridge.Blue;
import com.baeldung.bridge.Red;
import com.baeldung.bridge.Shape;
import com.baeldung.bridge.Square;
import com.baeldung.bridge.Triangle;

public class BridgePatternIntegrationTest {
	
    @Test
    public void whenBridgePatternInvoked_thenConfigSuccess() {
        //a square with red color
        Shape square = new Square(new Red());
        assertEquals(square.draw(), "Square drawn. Color is Red");
        
        //a triangle with blue color
        Shape triangle = new Triangle(new Blue());
        assertEquals(triangle.draw(), "Triangle drawn. Color is Blue");
    }
}


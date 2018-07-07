package com.baeldung.designpatterns;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.designpatterns.bridge.Blue;
import com.baeldung.designpatterns.bridge.Red;
import com.baeldung.designpatterns.bridge.Shape;
import com.baeldung.designpatterns.bridge.Square;
import com.baeldung.designpatterns.bridge.Triangle;

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


package com.baeldung.prototype;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PrototypeIntegrationTest {

    @Test
    public void givenCircleObjectWhenCloneIsInvokedThenReturnClonedObject() {
        List<String> metadata = new ArrayList<>();
        metadata.add("Circle");

        Circle circle = new Circle(5, metadata);

        Circle cloneCircle = circle.clone();

        assertTrue(cloneCircle != circle);
    }

    @Test
    public void givenSquareObjectWhenCloneIsInvokedThenReturnClonedObject() {
        List<String> metadata = new ArrayList<>();
        metadata.add("Square");

        Square square = new Square(5, metadata);

        Square cloneSquare = square.clone();

        assertTrue(cloneSquare != square);
    }

}

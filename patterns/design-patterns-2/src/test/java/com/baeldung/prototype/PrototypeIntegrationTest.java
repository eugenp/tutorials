package com.baeldung.prototype;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PrototypeIntegrationTest {

    @Test
    public void givenSquareObjectWhenCloneIsInvokedThenReturnClonedObject() {
        Circle circle = new Circle(5);

        Circle cloneCircle = circle.clone();

        assertTrue(cloneCircle != circle);
    }

    @Test
    public void givenCircleObjectWhenCloneIsInvokedThenReturnClonedObject() {
        Square square = new Square(5);

        Square cloneSquare = square.clone();

        assertTrue(cloneSquare != square);
    }

}

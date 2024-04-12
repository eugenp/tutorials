package com.baeldung.features;

import com.baeldung.features.JEP409.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class JEP409UnitTest {

    static class WeirdTriangle extends Triangle {
        @Override public int getNumberOfSides() {
            return 40;
        }
    }

    @Test
    void testSealedClass_shouldInvokeRightClass() {

        Shape shape = spy(new WeirdTriangle());

        int numberOfSides = getNumberOfSides(shape);

        assertEquals(40, numberOfSides);
        verify(shape).getNumberOfSides();
    }

    int getNumberOfSides(Shape shape) {
        return switch (shape) {
            case WeirdTriangle t -> t.getNumberOfSides();
            case Circle c -> c.getNumberOfSides();
            case Triangle t -> t.getNumberOfSides();
            case Rectangle r -> r.getNumberOfSides();
            case Square s -> s.getNumberOfSides();
        };
    }
}
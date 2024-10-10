package com.baeldung.interfaces.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ParameterizedUnitTest {

    @ParameterizedTest
    @MethodSource("data")
    void givenShapeInstance_whenAreaIsCalculated_thenSuccessful(Shape shapeInstance, double expectedArea) {
        double area = shapeInstance.area();
        assertEquals(expectedArea, area);

    }

    private static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
          { new Circle(5), 78.5 },
          { new Rectangle(4, 5), 20 }
        });
    }
}

package com.baeldung.nullchecking;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NullCheckUnitTest {

    @Test
    public void givenNullFields_whenCheckForNullsUsingIfs_thenReturnCorrectValue(){
        Car car = new Car();

        boolean result = car.allNull();

        assertTrue(result);
    }

    @Test
    public void givenNullFields_whenCheckForNullsUsingStreams_thenReturnCorrectValue(){
        Car car = new Car();

        boolean result = car.allNullV2();

        assertTrue(result);
    }

    @Test
    public void givenNullFields_whenCheckForNullsUsingApacheCommons_thenReturnCorrectValue(){
        Car car = new Car();

        boolean result = ObjectUtils.allNull(car.power, car.year);

        assertTrue(result);
    }

    @Test
    public void givenNullFields_whenCheckForNullsUsingReflection_thenReturnCorrectValue(){
        Car car = new Car();

        boolean result = NullChecker.allNull(car);

        assertTrue(result);
    }
}

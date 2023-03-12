package com.baeldung.oop.copying;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is for Unit testing.
 * <p>
 * Project Name: Spring boot
 *
 * @author Zahid Khan
 * @since 1.0
 */
class CarUnitTest {
    @Test
    void whenShallowCopy_thenReturnObjectShouldHaveDistinctReturnObjectReferenceOnly() {
        Car baseCar = new Car(new Engine("BS4 Engine"));
        Car shallowCar = baseCar.getShallowCopy();

        Assertions.assertNotSame(baseCar, shallowCar);
        Assertions.assertSame(baseCar.engine(), shallowCar.engine());

    }

    @Test
    void whenDeepCopy_thenReturnObjectShouldHaveAllDistinctReferences() {
        Car baseCar = new Car(new Engine("BS4 Engine"));
        Car deepCar = baseCar.getDeepCopy();

        Assertions.assertNotSame(baseCar, deepCar);
        Assertions.assertNotSame(baseCar.engine(), deepCar.engine());
    }

}
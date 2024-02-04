package com.baeldung.deepvsshallowcopy;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeepvsShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopy_thenSameChildObjects() {
        Engine originalEngine = new Engine(8, 6);
        CarWithShallowCopyConstructor originalCar = new CarWithShallowCopyConstructor(originalEngine);

        CarWithShallowCopyConstructor shallowCopyCar = new CarWithShallowCopyConstructor(originalCar);

        //Assert originalCar as different from shallowCopyCar
        assertNotEquals(originalCar, shallowCopyCar);
        //Assert that engine objects of both the Cars are same (shallow copy)
        assertEquals(originalCar.engine, shallowCopyCar.engine);
    }

    @Test
    public void whenCreatingDeepCopy_thenDifferentChildObjects() {
        Engine originalEngine = new Engine(8, 6);
        CarWithDeepCopyConstructor originalCar = new CarWithDeepCopyConstructor(originalEngine);

        CarWithDeepCopyConstructor deepCopyCar = new CarWithDeepCopyConstructor(originalCar);

        //Assert originalCar as different from shallowCopyCar
        assertNotEquals(originalCar, deepCopyCar);
        //Assert that engine objects of both the Cars are same (shallow copy)
        assertNotEquals(originalCar.engine, deepCopyCar.engine);
    }
}

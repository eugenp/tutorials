package com.baeldung.deepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.cloning.deep.Car;
import com.baeldung.cloning.deep.Engine;

public class DeepCopyExampleTest {
    private Car originalCar;

    @BeforeEach
    public void setUp() {
        Engine engine = new Engine("V6");
        originalCar = new Car("Toyota", "Camry", engine);
    }

    @Test
    public void testDeepCopy() {
        Car deepCopyCar = originalCar.clone();
        deepCopyCar.setMake("Honda");
        deepCopyCar.setModel("V8");

        Assertions.assertEquals("Toyota", originalCar.getMake());
        Assertions.assertEquals("Camry", originalCar.getModel());
        Assertions.assertNotSame(originalCar.getEngine(), deepCopyCar.getEngine());
    }
}

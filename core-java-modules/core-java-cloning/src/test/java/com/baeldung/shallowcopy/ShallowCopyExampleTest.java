package com.baeldung.shallowcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.cloning.shallow.Car;
import com.baeldung.cloning.shallow.Engine;

public class ShallowCopyExampleTest {

    private Car originalCar;

    @BeforeEach
    public void setUp() {
        Engine engine = new Engine("V6"); // V6 is Original Chessis Number
        originalCar = new Car("Toyota", "Camry", engine);
    }

    @Test
    public void testShallowCopy() {
        Car shallowCopyCar = originalCar.clone();
        shallowCopyCar.setMake("Honda");
        shallowCopyCar.setModel("Rev");
        shallowCopyCar.getEngine()
            .setChessisNumber("V8"); // V8 is new chessis number

        Assertions.assertEquals("Toyota", originalCar.getMake()); //
        Assertions.assertEquals("Camry", originalCar.getModel());
        Assertions.assertSame(shallowCopyCar.getEngine(), originalCar.getEngine());
    }
}
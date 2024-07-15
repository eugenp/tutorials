package com.baeldung.copies.shallow;

import com.baeldung.copies.Engine;
import com.baeldung.copies.FuelTank;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Nested
    public class CopyConstructor {
        @Test
        public void changingTheCloneFuelTank_shouldBeVisibleOnOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = new Car(car);
            shallowCopy.getFuelTank().fuel(10);

            assertEquals(car.getFuelTank().getGasMl(), 110);
        }

        @Test
        public void copyIsSharingTheEngineAndFuelTankAndMake_shouldHaveTheSameReferencesAsOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = new Car(car);

            assertAll(
                    () -> assertNotSame(car, shallowCopy),
                    () -> assertSame(car.getEngine(), shallowCopy.getEngine()),
                    () -> assertSame(car.getFuelTank(), shallowCopy.getFuelTank()),
                    () -> assertSame(car.getMake(), shallowCopy.getMake())
            );
        }

        @Test
        public void changingTheNumberOfDoorsOnCopy_shouldBeReflectedOnOriginal() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = new Car(car);
            shallowCopy.setNumberOfDoors(4);

            assertNotEquals(car.getNumberOfDoors(), shallowCopy.getNumberOfDoors());
        }
    }

    @Nested
    public class Cloneable {
        @Test
        public void changingTheCloneFuelTank_shouldBeVisibleOnOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = car.clone();
            shallowCopy.getFuelTank().fuel(10);

            assertEquals(car.getFuelTank().getGasMl(), 110);
        }

        @Test
        public void copyIsSharingTheEngineAndFuelTankAndMake_shouldHaveTheSameReferencesAsOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = car.clone();

            assertAll(
                    () -> assertNotSame(car, shallowCopy),
                    () -> assertSame(car.getEngine(), shallowCopy.getEngine()),
                    () -> assertSame(car.getFuelTank(), shallowCopy.getFuelTank()),
                    () -> assertSame(car.getMake(), shallowCopy.getMake())
            );
        }

        @Test
        public void changingTheNumberOfDoorsOnCopy_shouldBeReflectedOnOriginal() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = car.clone();
            shallowCopy.setNumberOfDoors(4);

            assertNotEquals(car.getNumberOfDoors(), shallowCopy.getNumberOfDoors());
        }
    }

}
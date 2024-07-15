package com.baeldung.copies.deep;

import com.baeldung.copies.Engine;
import com.baeldung.copies.FuelTank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Nested
    public class CloneableAsMarker {

        @Test
        public void clone_onClassWhich_shouldThrowCloneNotSupportedException() {
            Unmarked unmarked = new Unmarked();

            Assertions.assertThrows(CloneNotSupportedException.class, () -> unmarked.clone());
        }

        @Test
        public void clone_shouldNotThrowCloneNotSupportedException() throws CloneNotSupportedException {
            Unmarked marked = new MarkedWithCloneable();

            marked.clone();
        }

        public class Unmarked {

            @Override
            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

        }

        public class MarkedWithCloneable extends Unmarked implements java.lang.Cloneable {

        }

    }

    @Nested
    public class CopyConstructor {
        @Test
        public void changingTheCloneFuelTank_shouldNotBeVisibleOnOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = new Car(car);
            shallowCopy.getFuelTank().fuel(10);

            assertEquals(car.getFuelTank().getGasMl(), 100);
        }

        @Test
        public void copyIsSharingTheEngineAndFuelTankAndMake_shouldNotShareAnyReferenceWithOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = new Car(car);

            assertAll(
                    () -> assertNotSame(car, shallowCopy),
                    () -> assertNotSame(car.getEngine(), shallowCopy.getEngine()),
                    () -> assertNotSame(car.getFuelTank(), shallowCopy.getFuelTank()),
                    () -> assertNotSame(car.getMake(), shallowCopy.getMake())
            );
        }

        @Test
        public void changingTheNumberOfDoorsOnCopy_shouldNotBeReflectedOnOriginal() {
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
        public void changingTheCloneFuelTank_shouldNotBeVisibleOnOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = car.clone();
            shallowCopy.getFuelTank().fuel(10);

            assertEquals(car.getFuelTank().getGasMl(), 100);
        }

        @Test
        public void copyIsSharingTheEngineAndFuelTankAndMake_shouldNotShareAnyReferenceWithOriginalObject() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = car.clone();

            assertAll(
                    () -> assertNotSame(car, shallowCopy),
                    () -> assertNotSame(car.getEngine(), shallowCopy.getEngine()),
                    () -> assertNotSame(car.getFuelTank(), shallowCopy.getFuelTank()),
                    () -> assertNotSame(car.getMake(), shallowCopy.getMake())
            );
        }

        @Test
        public void changingTheNumberOfDoorsOnCopy_shouldNotBeReflectedOnOriginal() {
            FuelTank fuelTank = new FuelTank(100);
            Engine engine = new Engine(4, 200, 100);
            Car car = new Car(2, "Test Car", engine, fuelTank);

            Car shallowCopy = car.clone();
            shallowCopy.setNumberOfDoors(4);

            assertNotEquals(car.getNumberOfDoors(), shallowCopy.getNumberOfDoors());
        }
    }

}
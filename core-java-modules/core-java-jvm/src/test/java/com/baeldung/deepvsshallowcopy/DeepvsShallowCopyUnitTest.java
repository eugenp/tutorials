package com.baeldung.deepvsshallowcopy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeepvsShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopy_thenSameObject() {
        List<Wheel> mazdaWheels = new ArrayList<>();
        mazdaWheels.add(new Wheel("Front-Left", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Front-Right", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Back-Left", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Back-Right", Boolean.TRUE));

        Car mazda = new Car(4
                , new Chasis(123)
                , new Engine(4, 6)
                , "Mazda"
                , mazdaWheels
        );

        Car newMazda = mazda;

        assertEquals(newMazda, mazda);
        assertEquals(newMazda.wheels, mazda.wheels);
        assertEquals(newMazda.chasis, mazda.chasis);
        assertEquals(newMazda.engine, mazda.engine);
    }

    @Test
    public void whenCreatingWrongDeepCopy_thenSameChildObjects() {

        List<Wheel> mazdaWheels = new ArrayList<>();
        mazdaWheels.add(new Wheel("Front-Left", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Front-Right", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Back-Left", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Back-Right", Boolean.TRUE));

        Car mazda = new Car(4
                , new Chasis(123)
                , new Engine(4, 6)
                , "Mazda"
                , mazdaWheels
        );

        //  wrong way to create deep copy
        Car toyotaCorrollaShallowCopy = new Car(4
                , mazda.chasis
                , mazda.engine
                , "Toyota"
                , mazda.wheels);

        assertNotEquals(toyotaCorrollaShallowCopy, mazda);
        assertEquals(toyotaCorrollaShallowCopy.wheels, mazda.wheels);
        assertEquals(toyotaCorrollaShallowCopy.chasis, mazda.chasis);
        assertEquals(toyotaCorrollaShallowCopy.engine, mazda.engine);
    }

    @Test
    public void whenCreatingDeepCopy_thenDifferentObject() {

        List<Wheel> mazdaWheels = new ArrayList<>();
        mazdaWheels.add(new Wheel("Front-Left", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Front-Right", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Back-Left", Boolean.TRUE));
        mazdaWheels.add(new Wheel("Back-Right", Boolean.TRUE));

        Car mazda = new Car(4
                , new Chasis(123)
                , new Engine(4, 6)
                , "Mazda"
                , mazdaWheels
        );

        //  right way to create deep copy
        List<Wheel> toyotaWheels = new ArrayList<>();
        mazda.wheels.forEach(w ->
                toyotaWheels.add(new Wheel(w.wheelPosition, w.hasRim)));
        Car toyotaCorrollaDeepCopy = new Car(4
                , new Chasis(mazda.chasis.chasisNumber)
                , new Engine(mazda.engine.numberOfValves, mazda.engine.numberOfGears)
                , "Toyota"
                , toyotaWheels
        );

        assertNotEquals(toyotaCorrollaDeepCopy, mazda);
        assertNotEquals(toyotaCorrollaDeepCopy.wheels, mazda.wheels);
        assertNotEquals(toyotaCorrollaDeepCopy.engine, mazda.engine);
        assertNotEquals(toyotaCorrollaDeepCopy.chasis, mazda.chasis);
        assertNotEquals(toyotaCorrollaDeepCopy.manufacturerName, mazda.manufacturerName);
    }
}

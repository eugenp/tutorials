package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class PolymorphismUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", dinoBehavior(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {
        assertEquals("calm", dinoBehavior(new Euraptor()));
    }

    public static String dinoBehavior(Dinosaur dinosaur) {
        return dinosaur.move();
    }

}

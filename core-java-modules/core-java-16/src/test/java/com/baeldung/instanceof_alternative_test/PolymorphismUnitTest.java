package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class PolymorphismUnitTest {

    @Test
    public void givenADinosaurSpecie_whenUsingPolymorphism_thenGetMovementOfAnatotitan() {

        assertEquals("running", moveDinosaurUsingPolymorphism(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenUsingPolymorphism_thenGetMovementOfEuraptor() {
        assertEquals("flying", moveDinosaurUsingPolymorphism(new Euraptor()));
    }

    public static String moveDinosaurUsingPolymorphism(Dinosaur dinosaur) {
        return dinosaur.move();
    }

}

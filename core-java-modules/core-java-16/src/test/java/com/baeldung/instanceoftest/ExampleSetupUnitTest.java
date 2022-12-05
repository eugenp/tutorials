package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class ExampleSetupUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", dinoBehavior(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {
        assertEquals("flying", dinoBehavior(new Euraptor()));
    }

    public static String dinoBehavior(Dinosaur dinosaur) {

        if (dinosaur instanceof Anatotitan) {

            Anatotitan anatotitan = (Anatotitan) dinosaur;
            return anatotitan.run();
        } else if (dinosaur instanceof Euraptor) {
            Euraptor euraptor = (Euraptor) dinosaur;
            return euraptor.flies();
        }
        return "";
    }

}

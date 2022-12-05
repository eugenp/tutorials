package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class PatternMatchingUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", dinoBehavior(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {
        assertEquals("flying", dinoBehavior(new Euraptor()));
    }

    public static String dinoBehavior(Dinosaur dinosaur) {

        if (dinosaur instanceof Anatotitan anatotitan) {
            return anatotitan.run();
        } else if (dinosaur instanceof Euraptor euraptor) {

            return euraptor.flies();
        }
        return "";
    }

}

package com.baeldung.instanceoftest;

import static org.junit.Assert.*;
import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class GetClassUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", dinoBehavior(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {
        assertEquals("calm", dinoBehavior(new Euraptor()));
    }

    public static String dinoBehavior(Dinosaur dinosaur) {

        if (dinosaur.getClass()
            .equals(Anatotitan.class)) {

            Anatotitan anatotitan = (Anatotitan) dinosaur;
            return anatotitan.run();
        } else if (dinosaur.getClass()
            .equals(Euraptor.class)) {
            Euraptor euraptor = (Euraptor) dinosaur;
            return euraptor.flies();
        }
        return "";
    }

}

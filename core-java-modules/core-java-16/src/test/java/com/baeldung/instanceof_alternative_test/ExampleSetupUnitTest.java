package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class ExampleSetupUnitTest {

    @Test
    public void givenADinosaurSpecie_whenUsingInstancof_thenGetMovementOfAnatotitan() {

        assertEquals("running", moveDinosaurUsingInstanceof(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenUsingInstanceof_thenGetMovementOfEuraptor() {
        assertEquals("flying", moveDinosaurUsingInstanceof(new Euraptor()));
    }

    public static String moveDinosaurUsingInstanceof(Dinosaur dinosaur) {

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

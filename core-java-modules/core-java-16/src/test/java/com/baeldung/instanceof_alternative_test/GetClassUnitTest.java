package com.baeldung.instanceoftest;

import static org.junit.Assert.*;
import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class GetClassUnitTest {

    @Test
    public void givenADinosaurSpecie_whenUsingGetClass_thenGetMovementOfAnatotitan() {

        assertEquals("running", moveDinosaurUsingGetClass(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenUsingGetClass_thenGetMovementOfEuraptor() {
        assertEquals("flying", moveDinosaurUsingGetClass(new Euraptor()));
    }

    public static String moveDinosaurUsingGetClass(Dinosaur dinosaur) {

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

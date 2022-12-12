package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.visitorspattern.*;

public class VisitorsPatternUnitTest {

    @Test
    public void givenADinosaurSpecie_whenUsingVisitorPattern_thenGetMovementOfAnatotitan() {

        assertEquals("running", moveDinosaurUsingVisitorPattern((Dino) new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenUsingVisitorPattern_thenGetMovementOfEuraptor() {

        assertEquals("flying", moveDinosaurUsingVisitorPattern((Dino) new Euraptor()));
    }

    public static String moveDinosaurUsingVisitorPattern(Dino dinosaur) {
        Visitor visitor = new DinoVisitorImpl();

        return dinosaur.move(visitor);
    }

}

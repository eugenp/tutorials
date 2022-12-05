package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.visitorspattern.*;

public class VisitorsPatternUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", visitorsPatter((Dino) new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {

        assertEquals("flying", visitorsPatter((Dino) new Euraptor()));
    }

    public static String visitorsPatter(Dino dinosaur) {
        Visitor visitor = new DinoVisitorImpl();

        return dinosaur.move(visitor);
    }

}

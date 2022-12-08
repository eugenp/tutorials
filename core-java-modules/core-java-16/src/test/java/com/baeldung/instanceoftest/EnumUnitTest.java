package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.enumallt.*;

public class EnumUnitTest {
    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {

        assertEquals("flying", moveDinosaur(DinosaurEnum.Euraptor));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {
        assertEquals("running", moveDinosaur(DinosaurEnum.Anatotitan));
    }

    public static String moveDinosaur(DinosaurEnum dinosaurenum) {
        return dinosaurenum.move();

    }

}
package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.enumallt.*;

public class EnumUnitTest {
    @Test
    public void givenADinosaurSpecie_whenUsingEnum_thenGetMovementOfEuraptor() {

        assertEquals("flying", moveDinosaurUsingEnum(DinosaurEnum.Euraptor));
    }

    @Test
    public void givenADinosaurSpecie_whenUsingEnum_thenGetMovementOfAnatotitan() {
        assertEquals("running", moveDinosaurUsingEnum(DinosaurEnum.Anatotitan));
    }

    public static String moveDinosaurUsingEnum(DinosaurEnum dinosaurenum) {
        return dinosaurenum.move();

    }

}
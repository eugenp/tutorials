package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.enumallt.*;

public class EnumUnitTest {
    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {

        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinosaur(DinosaurEnum.Euraptor);

        assertEquals("flying", dinosaur.getDinosaur()
            .move());
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinosaur(DinosaurEnum.Anatotitan);

        assertEquals("running", dinosaur.getDinosaur()
            .move());
    }

}
package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.enumallt.*;

public class EnumUnitTest {
    @Test
    public void testAnatotitan() {

        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinosaur(DinosaurEnum.Euraptor);

        assertEquals("Calm", dinosaur.getDinosaur()
            .behaviour());
    }

    @Test
    public void testEuraptor() {
        Dinosaur dinosaur = new Dinosaur();
        dinosaur.setDinosaur(DinosaurEnum.Anatotitan);

        assertEquals("Aggressive  ", dinosaur.getDinosaur()
            .behaviour());
    }

}
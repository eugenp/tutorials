package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;
import com.baeldung.instanceofalternative.mapmethod.*;

public class MapUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", moveDinosaur(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {

        assertEquals("flying", moveDinosaur(new Euraptor()));
    }

    public static String moveDinosaur(Dinosaur dinosaur) {
        MapAlternative movement = new MapAlternative();
        return movement.handleMessage(dinosaur);
    }

}

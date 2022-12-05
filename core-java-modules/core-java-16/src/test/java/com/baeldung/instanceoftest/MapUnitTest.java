package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.baeldung.instanceofalternative.model.*;

public class MapUnitTest {

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfAnatotitan() {

        assertEquals("running", moveDinosaur(new Anatotitan()));
    }

    @Test
    public void givenADinosaurSpecie_whenGroupBySubclass_thenGetMovementOfEuraptor() {

        assertEquals("calm", moveDinosaur(new Euraptor()));
    }

    public static String moveDinosaur(Dinosaur dinosaur) {
        Map<Class<? extends Dinosaur>, String> response = new HashMap<Class<? extends Dinosaur>, String>();

        response.put(dinosaur.getClass(), dinosaur.move());
        return response.get(dinosaur.getClass());
    }

}

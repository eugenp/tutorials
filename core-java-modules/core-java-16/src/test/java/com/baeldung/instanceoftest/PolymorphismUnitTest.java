package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.instanceofalternative.polymorphism.*;

public class PolymorphismUnitTest {

    @Test
    public void testAnatotitan() {

        assertEquals("very aggressive", dinoBehavior(new Anatotitan()));
    }

    @Test
    public void testEuraptor() {
        assertEquals("calm", dinoBehavior(new Euraptor()));
    }

    public static String dinoBehavior(Dinosaur dinosaur) {
        return dinosaur.behavior();
    }

}

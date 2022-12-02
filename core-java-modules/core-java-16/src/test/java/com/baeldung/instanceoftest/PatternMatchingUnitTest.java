package com.baeldung.instanceoftest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.baeldung.instanceofalternative.patternmatching.*;

public class PatternMatchingUnitTest {

    @Test
    public void testAnatotitan() {

        assertEquals("very aggressive", dinoBehavior(new Anatotitan()));
    }

    @Test
    public void testEuraptor() {
        assertEquals("calm", dinoBehavior(new Euraptor()));
    }

    public static String dinoBehavior(Dinosaur dinosaur) {

        if (dinosaur instanceof Anatotitan anatotitan) {
            return anatotitan.behavior();
        } else if (dinosaur instanceof Euraptor euraptor) {

            return euraptor.behavior();
        }
        return "";
    }

}

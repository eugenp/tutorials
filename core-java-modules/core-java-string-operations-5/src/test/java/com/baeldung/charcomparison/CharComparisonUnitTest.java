package com.baeldung.charcomparison;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class CharComparisonUnitTest {

    @Test
    void givenTwoPrimitiveChars_whenUsingRelationalOperators_thenCompare() {
        assertFalse('a' == 'A');
        assertTrue('a' < 'v');
        assertTrue('F' > 'D');
    }

    @Test
    void givenTwoPrimitiveChars_whenUsingCompareMethod_thenCompare() {
        assertTrue(Character.compare('C', 'C') == 0);
        assertTrue(Character.compare('f', 'A') > 0);
        assertTrue(Character.compare('Y', 'z') < 0);
    }

    @Test
    void givenTwoCharObjects_whenUsingCompareToMethod_thenCompare() {
        Character chK = Character.valueOf('K');
        Character chG = Character.valueOf('G');
        Character chH = Character.valueOf('H');

        assertTrue(chK.compareTo(chK) == 0);
        assertTrue(chK.compareTo(chG) > 0);
        assertTrue(chG.compareTo(chH) < 0);
    }

    @Test
    void givenTwoCharObjects_whenUsingEqualsMethod_thenCompare() {
        Character chL = 'L';
        Character chV = 'V';

        assertTrue(chL.equals(chL));
        assertFalse(chL.equals(chV));
    }

    @Test
    void givenTwoCharObjects_whenUsingObjectsEqualsMethod_thenCompare() {
        Character chA = 'A';
        Character chB = 'B';

        assertTrue(Objects.equals(chA, chA));
        assertFalse(Objects.equals(chA, chB));
    }

}

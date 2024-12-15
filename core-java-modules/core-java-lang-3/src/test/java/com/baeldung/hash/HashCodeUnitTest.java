package com.baeldung.hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Objects;

import org.junit.Test;

public class HashCodeUnitTest {

    @Test
    public void whenCallingObjectsHashCodeOnIndenticalObjects_thenSameHashCodeReturned() {
        String stringOne = "test";
        String stringTwo = "test";
        int hashCode1 = Objects.hashCode(stringOne);
        int hashCode2 = Objects.hashCode(stringTwo);

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void whenCallingObjectsHashCodeOnNullObject_thenZeroReturned() {
        String nullString = null;
        int hashCode = Objects.hashCode(nullString);
        assertEquals(0, hashCode);
    }

    @Test
    public void whenCallingObjectHashCodeOnIndenticalObjects_thenSameHashCodeReturned() {
        Double valueOne = Double.valueOf(1.0012);
        Double valueTwo = Double.valueOf(1.0012);

        int hashCode1 = valueOne.hashCode();
        int hashCode2 = valueTwo.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test(expected = NullPointerException.class)
    public void whenCallingObjectHashCodeOnNullObject_theNullPointerExceptionThrown() {
        Double value = null;
        value.hashCode();
    }

    @Test
    public void whenCallingObjectsHashOnStrings_thenSameHashCodeReturned() {
        String strOne = "one";
        String strTwo = "two";
        String strOne2 = "one";
        String strTwo2 = "two";

        int hashCode1 = Objects.hash(strOne, strTwo);
        int hashCode2 = Objects.hash(strOne2, strTwo2);

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void whenCallingObjectsHashOnSingleString_thenDifferentHashcodeFromObjectsHashCodeCallReturned() {
        String testString = "test string";
        int hashCode1 = Objects.hash(testString);
        int hashCode2 = Objects.hashCode(testString);

        assertNotEquals(hashCode1, hashCode2);
    }
}

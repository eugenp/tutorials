package com.baeldung.objecttostring;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PersonWithHashCodeUnitTest {
    @Test
    public void givenObject_whenNoToString_thenReturn() {
        PersonWithHashCode person = new PersonWithHashCode("Sarah", 28);
        String expectedToString = "com.baeldung.objecttostring.PersonWithHashCode@" + Integer.toHexString(person.hashCode());
        String actual = person.toString();
        assertEquals(expectedToString, actual);
    }
}

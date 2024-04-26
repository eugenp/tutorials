package com.baeldung.junit.equalsvssame;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EqualsVsSameUnitTest {

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "World"})
    void givenAString_WhenCompare_ThenItEqualsAndSame(String string) {
        assertEquals(string, string);
        assertSame(string, string);
    }

    public boolean equals(Object obj) {
        return (this == obj);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "World"})
    void givenAStrings_WhenCompareNewStrings_ThenItEqualsButNotSame(String string) {
        assertEquals(new String(string), new String(string));
        assertNotSame(new String(string), new String(string));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "World"})
    void givenAString_WhenCompareInJava_ThenItEqualsAndSame(String string) {
        assertTrue(string.equals(string));
        assertTrue(string == string);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "World"})
    void givenAStrings_WhenCompareNewStringsInJava_ThenItEqualsButNotSame(String string) {
        assertTrue(new String(string).equals(new String(string)));
        assertFalse(new String(string) == new String(string));
    }

    @Test
    void givePeople_WhenCompareWithoutOverridingEquals_TheyNotEqual() {
        Person firstPerson = new Person("John", "Doe");
        Person secondPerson = new Person("John", "Doe");
        assertNotEquals(firstPerson, secondPerson);
    }


}

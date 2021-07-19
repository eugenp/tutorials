package com.baeldung.initialization;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringInitializationUnitTest {

    private String fieldString;

    void printDeclaredOnlyString() {
        String localVarString = null;

        System.out.println(localVarString); // compilation error
        System.out.println(fieldString);
    }

    @Test
    public void givenDeclaredFeldStringAndNullString_thenCompareEquals() {
        String localVarString = null;

        assertEquals(fieldString, localVarString);
    }

    @Test
    public void givenTwoStringsWithSameLiteral_thenCompareReferencesEquals() {
        String literalOne = "Baeldung";
        String literalTwo = "Baeldung";

        assertTrue(literalOne == literalTwo);
    }

    @Test
    public void givenTwoStringsUsingNew_thenCompareReferencesNotEquals() {
        String newStringOne = new String("Baeldung");
        String newStringTwo = new String("Baeldung");

        assertFalse(newStringOne == newStringTwo);
    }

    @Test
    public void givenEmptyLiteralStringsAndNewObject_thenCompareEquals() {
        String emptyLiteral = "";
        String emptyNewString = new String("");

        assertEquals(emptyLiteral, emptyNewString);
    }

    @Test
    public void givenEmptyStringObjects_thenCompareEquals() {
        String emptyNewString = new String("");
        String emptyNewStringTwo = new String();

        assertEquals(emptyNewString, emptyNewStringTwo);
    }
}

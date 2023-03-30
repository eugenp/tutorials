package com.baeldung.booleans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BooleanToStringUnitTest {
    String optionToString(String optionName, boolean optionValue) {
        return String.format("The option '%s' is %s.", optionName, optionValue ? "Enabled" : "Disabled");
    }

    @Test
    void givenPrimitiveBoolean_whenConvertingUsingTernaryOperator_thenSuccess() {
        boolean primitiveBoolean = true;
        assertEquals("true", primitiveBoolean ? "true" : "false");

        primitiveBoolean = false;
        assertEquals("false", primitiveBoolean ? "true" : "false");

        assertEquals("The option 'IgnoreWarnings' is Enabled.", optionToString("IgnoreWarnings", true));
    }


    @Test
    void givenBooleanObject_whenConvertingUsingBooleanToString_thenSuccess() {
        Boolean myBoolean = Boolean.TRUE;
        assertEquals("true", myBoolean.toString());

        myBoolean = Boolean.FALSE;
        assertEquals("false", myBoolean.toString());

        Boolean nullBoolean = null;
        assertThrows(NullPointerException.class, () -> nullBoolean.toString());
    }

    @Test
    void givenBooleanObject_whenConvertingUsingStringValueOf_thenSuccess() {
        Boolean myBoolean = Boolean.TRUE;
        assertEquals("true", String.valueOf(myBoolean));

        myBoolean = Boolean.FALSE;
        assertEquals("false", String.valueOf(myBoolean));

        Boolean nullBoolean = null;
        assertEquals("null", String.valueOf(nullBoolean));
    }
}
package com.baeldung.lombok.extensionmethod;

import lombok.experimental.ExtensionMethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtensionMethod(StringUtils.class)
public class StringUtilsUnitTest {

    @Test
    public void givenString_whenUsingExtensionMethod_thenReverseString() {
        String original = "Lombok Extension Method";
        String reversed = original.reverse();
        assertEquals("dohteM noisnetxE kobmoL", reversed);
    }

    @Test
    public void givenEmptyString_whenUsingExtensionMethod_thenReverseString() {
        String original = "";
        String reversed = original.reverse();
        assertEquals("", reversed);
    }

    @Test
    public void givenSingleCharString_whenUsingExtensionMethod_thenReverseString() {
        String original = "A";
        String reversed = original.reverse();
        assertEquals("A", reversed);
    }

    @Test
    public void givenMixedString_whenUsingExtensionMethod_thenReverseString() {
        String original = "Ab2Cd3Ef";
        String reversed = original.reverse();
        assertEquals("fE3dC2bA", reversed);
    }
}

package com.baeldung.lombok.extensionmethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringUtilsWithoutAnnotationUnitTest {

    @Test
    public void givenString_whenNotUsingExtensionMethod_thenReverseString() {
        String original = "Lombok Extension Method";
        String reversed = StringUtils.reverse(original);
        assertEquals("dohteM noisnetxE kobmoL", reversed);
    }
}

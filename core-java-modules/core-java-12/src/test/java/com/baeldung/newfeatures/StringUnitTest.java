package com.baeldung.newfeatures;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class StringUnitTest {

    @Test
    public void givenString_thenRevertValue() {
        String text = "Baeldung";
        String transformed = text.transform(value -> new StringBuilder(value).reverse().toString());
        assertEquals("gnudleaB", transformed);
    }
}

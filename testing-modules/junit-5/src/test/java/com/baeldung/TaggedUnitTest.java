package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Test case")
public class TaggedUnitTest {

    @Test
    @Tag("Method")
    void testMethod() {
        assertEquals(2 + 2, 4);
    }
}

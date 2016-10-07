package com.baeldung;

import org.junit.gen5.api.Tag;
import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;

@Tag("Test case")
public class TaggedTest {

    @Test
    @Tag("Method")
    void testMethod() {
        assertEquals(2 + 2, 4);
    }
}

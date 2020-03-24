package com.stackify.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("math")
public class TaggedTest {

    @Test
    @Tag("arithmetic")
    public void testEquals() {
        assertTrue(1 == 1);
    }

}

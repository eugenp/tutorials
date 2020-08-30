package com.baeldung.junit5.nonstatic;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeforeAfterAllNonStaticTest {

    String input;
    Long result;

    @BeforeAll
    public void setup() {
        input = "77";
    }

    @AfterAll
    public void teardown() {
        Assertions.assertEquals(77l, result);
    }

    @Test
    public void testConvertStringToLong() {
        result = Long.valueOf(input);
    }
}

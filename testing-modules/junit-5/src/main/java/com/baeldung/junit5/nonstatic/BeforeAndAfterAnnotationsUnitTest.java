package com.baeldung.junit5.nonstatic;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeforeAndAfterAnnotationsUnitTest {

    String input;
    Long result;

    @BeforeAll
    public void setup() {
        input = "77";
    }

    @AfterAll
    public void teardown() {
        input = null;
        result = null;
    }

    @Test
    public void whenConvertStringToLong_thenResultShouldBeLong() {
        result = Long.valueOf(input);
        Assertions.assertEquals(77l, result);
    }
}

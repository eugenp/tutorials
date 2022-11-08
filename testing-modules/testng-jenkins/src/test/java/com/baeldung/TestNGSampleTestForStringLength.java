package com.baeldung;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestNGSampleTestForStringLength {

    String name;

    @BeforeClass
    public void setup() {
        name = "Hannah";
    }

    @Test
    public void givenString_whenEvenLength_thenTrue() {
        assertEquals(0, name.length() % 2);
    }
}

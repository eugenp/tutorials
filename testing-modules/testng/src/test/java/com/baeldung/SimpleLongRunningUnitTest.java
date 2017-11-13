package com.baeldung;

import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimpleLongRunningUnitTest extends TestNG {
    private int number;

    @BeforeClass
    public void setup() {
        number = 12;
    }

    @AfterClass
    public void tearDown() {
        number = 0;
    }

    @Test
    public void givenNumber_whenEven_thenTrue() {
        Assert.assertTrue(number % 2 == 0);
    }

}


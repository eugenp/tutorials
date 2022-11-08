package com.baeldung;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestNGSampleTestForNumberAddition {
    int firstNumber, secondNumber;

    @BeforeClass
    public void setup() {
        firstNumber = 13;
        secondNumber = 24;
    }

    @AfterClass
    public void tearDown() {
        firstNumber = secondNumber = 0;
    }

    @Test
    public void givenNumbers_whenSumIsOdd_thenTrue() {
        assertEquals(1, ( firstNumber + secondNumber ) % 2);
    }
}

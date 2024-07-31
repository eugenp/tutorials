package com.baeldung;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PriorityLongRunningUnitTest {

    @Test(priority = 1)
    public void givenString_whenChangedToInt_thenCorrect() {
        String testString = "10";
        assertTrue(Integer.valueOf(testString) instanceof Integer);
    }

    @Test(priority = 2)
    public void givenInt_whenChangedToString_thenCorrect() {
        int testInt = 23;
        assertTrue(String.valueOf(testInt) instanceof String);
    }

}

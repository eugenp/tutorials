package com.baeldung.junit4;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

public class AssumeUnitTest {

    @Test
    public void trueAssumption() {
        assumeTrue("5 is greater the 1", 5 > 1);
        assertEquals(5 + 2, 7);
    }

    @Test
    public void falseAssumption() {
        assumeFalse("5 is less then 1", 5 < 1);
        assertEquals(5 + 2, 7);
    }
}

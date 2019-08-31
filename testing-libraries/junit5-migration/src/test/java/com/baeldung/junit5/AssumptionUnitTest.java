package com.baeldung.junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.*;

public class AssumptionUnitTest {

    @Test
    public void trueAssumption() {
        assumeTrue(5 > 1, () -> "5 is greater the 1");
        assertEquals(5 + 2, 7);
    }

    @Test
    public void falseAssumption() {
        assumeFalse(5 < 1, () -> "5 is less then 1");
        assertEquals(5 + 2, 7);
    }

    @Test
    public void assumptionThat() {
        String someString = "Just a string";
        assumingThat(someString.equals("Just a string"), () -> assertEquals(2 + 2, 4));
    }
}

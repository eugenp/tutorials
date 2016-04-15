package com.baeldung;

import org.junit.gen5.api.Test;

import static org.junit.gen5.api.Assumptions.assumeFalse;
import static org.junit.gen5.api.Assumptions.assumeTrue;
import static org.junit.gen5.api.Assumptions.assumingThat;

public class AssumptionTest {

    @Test
    void trueAssumption() {
        assumeTrue(5 > 1);
        System.out.println("Was true");
    }

    @Test
    void falseAssumption() {
        assumeFalse(5 < 1);
        System.out.println("Was false");
    }

    @Test
    void assumptionThat() {
        String someString = "Just a string";
        assumingThat(
                someString.equals("Just a string"),
                () -> System.out.println("Assumption was correct")
        );
    }
}

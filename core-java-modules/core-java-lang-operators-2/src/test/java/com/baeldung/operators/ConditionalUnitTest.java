package com.baeldung.operators;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class ConditionalUnitTest {

    @Test
    void whenUseTheOperatorConditionalIncluive() {
        int years = 25;
        boolean driverLicence = true;
        String result = "";
        if (years >= 21 && driverLicence) {
            result = "Successful Candidate.";
        } else {
            result = "Failed Candidate.";
        }
        assertEquals(result, "Successful Candidate.");

    }

    @Test
    void whenUseTheOperatorConditionalInclusiveWithTernary() {
        int years = 25;
        boolean driverLicence = true;
        String result = (years >= 21 && driverLicence) ? "Successful Candidate." : "Successful Candidate.";
        assertEquals(result, "Successful Candidate.");

    }

    @Test
    void whenUseTheOperatorConditionalAlternative() {
        int years = 25;
        boolean driverLicence = false;
        String result = "";
        if ((years >= 21 || driverLicence)) {
            result = "Successful Candidate.";
        } else {
            result = "Failed Candidate.";
        }
        assertEquals(result, "Successful Candidate.");

    }

    @Test
    void whenUseTheOperatorConditionalAlternativeWithTernary() {
        int years = 25;
        boolean driverLicence = false;
        String result = (years >= 21 || driverLicence) ? "Successful Candidate." : "Successful Candidate.";
        assertEquals(result, "Successful Candidate.");

    }

}

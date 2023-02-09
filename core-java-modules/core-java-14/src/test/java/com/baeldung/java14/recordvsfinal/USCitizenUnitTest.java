package com.baeldung.java14.recordvsfinal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class USCitizenUnitTest {

    @Test
    public void givenName_whenGetNameAndCode_thenExpectedValuesReturned() {

        String firstName = "Joan";
        String lastName = "Winn";
        String address = "1892 Black Stallion Road";
        int countryCode = 1;

        USCitizen citizen = new USCitizen(firstName, lastName, address);

        assertEquals(firstName + " " + lastName, citizen.getFullName());
        assertEquals(countryCode, USCitizen.getCountryCode());
    }

}
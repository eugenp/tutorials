package com.baeldung.java.copy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test the shallow and deep copy processes.
 */
public class ShallowCopyUnitTest {

    /** The name for Company A. */
    public static final String COMPANY_A_NAME = "Company-A";
    /** The Steel industry. */
    public static final String STEEL_INDUSTRY = "Steel";
    /** The name for Company B. */
    public static final String COMPANY_B_NAME = "Company-B";
    /** The Automobile industry. */
    public static final String AUTOMOBILE_INDUSTRY = "Automobile Manufacturing";

    @Test
    public void whenTwoVariablesReferenceTheSameObject_thenTheyAreCoupled() {
        Company companyA = new Company(COMPANY_A_NAME, new Industry(STEEL_INDUSTRY));

        // Make a new company variable referencing companyA
        Company companyB = companyA;
        assertEquals(companyB, companyA);

        // Change the name of the new company
        companyB.setName("Company-B");
        assertEquals(COMPANY_B_NAME, companyB.getName());

        // Test that they are the same - CompanyA name has changed
        assertNotEquals(COMPANY_A_NAME, companyA.getName());
    }

    @Test
    public void whenAShallowCopyIsMade_thenTheFirstLevelIsDecoupled() {

        // Create an Industry
        Industry steelIndustry = new Industry(STEEL_INDUSTRY);
        // Create the first Company
        Company companyA = new Company(COMPANY_A_NAME, steelIndustry);

        // Make a new company as a copy
        Company companyB = new Company(COMPANY_B_NAME, companyA.getIndustry());

        // Test that company B is a copy with a different name
        assertNotEquals(companyA, companyB);
        assertNotEquals(companyA.getName(), companyB.getName());
        // Test that they share the same industry
        assertEquals(companyA.getIndustry(), companyB.getIndustry());
        companyB.getIndustry().setName(AUTOMOBILE_INDUSTRY);
        // companyA industry name has changed
        assertNotEquals(STEEL_INDUSTRY, companyA.getIndustry().getName());
    }

    @Test
    public void whenADeepCopyIsMade_thenTheObjectsAreCompletelyDecoupled() {

        // Create an Industry
        Industry steelIndustry = new Industry(STEEL_INDUSTRY);
        // Create the first Company
        Company companyA = new Company(COMPANY_A_NAME, steelIndustry);

        // Make a new company as a deep copy - note the new Industry() construction
        Company companyB = new Company(COMPANY_B_NAME,  new Industry(companyA.getIndustry().getName()));

        // Test that company B is a copy with a different name
        assertNotEquals(companyA, companyB);
        assertNotEquals(companyA.getName(), companyB.getName());

        // Change the name of the company's industry
        companyB.getIndustry().setName(AUTOMOBILE_INDUSTRY);
        // companyA industry name has changed
        assertEquals(STEEL_INDUSTRY, companyA.getIndustry().getName());
    }

}

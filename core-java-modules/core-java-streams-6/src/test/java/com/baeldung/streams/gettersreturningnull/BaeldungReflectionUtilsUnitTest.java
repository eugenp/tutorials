package com.baeldung.streams.gettersreturningnull;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BaeldungReflectionUtilsUnitTest {

    @Test
    public void givenCustomer_whenAFieldIsNull_thenFieldNameInResult() throws Exception {
        Customer customer = new Customer(1, "Himanshu", null, null);

        List<String> result = Utils.getNullPropertiesList(customer);
        List<String> expectedFieldNames = Arrays.asList("emailId", "phoneNumber");

        assertTrue(result.size() == expectedFieldNames.size());
        assertTrue(result.containsAll(expectedFieldNames));

    }

}

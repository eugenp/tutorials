package com.baeldung.reflection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BaeldungReflectionUtilsTest {

    @Test
    public void givenCustomer_whenAFieldIsNull_thenFieldNameInResult() throws Exception {
        Customer customer = new Customer(1, "Himanshu", null, null);

        List<String> result = BaeldungReflectionUtils.getNullPropertiesList(customer);
        List<String> expectedFieldNames = Arrays.asList("emailId", "phoneNumber");

        Assert.assertTrue(result.size() == expectedFieldNames.size());
        Assert.assertTrue(result.containsAll(expectedFieldNames));

    }

}

package com.baeldung.reflection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * 测试：反射
 */
public class BaeldungReflectionUtilsUnitTest {

    @Test
    public void givenCustomer_whenAFieldIsNull_thenFieldNameInResult() throws Exception {
        Customer customer = new Customer(1, "Himanshu", null, null);

        List<String> result = BaeldungReflectionUtils.getNullPropertiesList(customer);
        System.out.println("result:{}" + result);

        List<String> expectedFieldNames = Arrays.asList("emailId", "phoneNumber");

        assertTrue(result.size() == expectedFieldNames.size());
        assertTrue(result.containsAll(expectedFieldNames));
    }

}

package com.baeldung.domain;

import java.util.Arrays;
import java.util.List;

public class CustomerAccountChecker {

    private static List<String> blackListedIdentities = Arrays.asList("1234567", "2345678", "A1234567");

    public static boolean isCustomerBlacklisted(Customer customer) {
        return blackListedIdentities.contains(customer.identity());
    }

    public static boolean isIdentityValid(Customer customer) {
        return customer.identity().trim().length() < 5;
    }

}

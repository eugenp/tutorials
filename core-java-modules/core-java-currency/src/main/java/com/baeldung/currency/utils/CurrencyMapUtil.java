package com.baeldung.currency.utils;

import java.util.Map;

public class CurrencyMapUtil {
    private static final Map<String, String> currencymap = Map.of(
            "USD", "$",
            "EUR", "€",
            "INR", "₹"
    );

    public static String getSymbol(String currencyCode) {
        return currencymap.getOrDefault(currencyCode, "Unknown");
    }
}
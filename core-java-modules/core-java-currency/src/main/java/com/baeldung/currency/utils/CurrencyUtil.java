package com.baeldung.currency.utils;

import java.util.Currency;

public class CurrencyUtil {
    public static String getSymbol(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        return currency.getSymbol();
    }
}
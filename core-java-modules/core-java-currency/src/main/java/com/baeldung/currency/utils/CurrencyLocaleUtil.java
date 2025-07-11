package com.baeldung.currency.utils;

import java.util.Currency;
import java.util.Locale;

public class CurrencyLocaleUtil {
    public String getSymbolForLocale(Locale locale) {
        Currency currency = Currency.getInstance(locale);
        return currency.getSymbol();
    }
}
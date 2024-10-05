package com.baeldung.stringcontainscurrencysymbol;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencySymbolFinder {

    public static String currencySymbolMatcher(String symbol, String content) {
        Pattern pattern = Pattern.compile(symbol, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "";
    }

    public static String currencyNameMatcher(Locale locale, String content) {
        String symbol = Currency.getInstance(locale)
            .getSymbol();
        return currencySymbolMatcher(symbol, content);
    }

    public static Number parseCurrencyAmount(Locale locale, String input) {
        Number value;
        try {
            value = NumberFormat.getCurrencyInstance(locale)
                .parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}

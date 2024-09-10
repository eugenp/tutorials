package com.baeldung.stringcontainscurrencysymbol;

import java.util.Currency;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CurrencySymbolFinder {

    public static String singleSymbolMatcher(String symbol, String content) {
        Pattern pattern = Pattern.compile(symbol, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "";
    }

    public static String currencyNameMatcher(Locale locale, String content) {
        String symbol = Currency.getInstance(locale).getSymbol();
        return singleSymbolMatcher(symbol, content);
    }

    public static Set<String> getAvailableCurrencies() {
        return Currency.getAvailableCurrencies()
                .stream()
                .map(Currency::getSymbol)
                .collect(Collectors.toSet());
    }

    public static boolean multipleSymbolMatcher(String symbol, String content) {
        Pattern pattern = Pattern.compile(symbol, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }

}

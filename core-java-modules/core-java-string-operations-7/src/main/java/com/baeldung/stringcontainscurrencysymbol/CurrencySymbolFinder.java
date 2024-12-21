package com.baeldung.stringcontainscurrencysymbol;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencySymbolFinder {

    private static final String NUMERIC_REGEX = "(\\d{1,3}(,\\d{3}|\\s\\d{3})*|\\d+)(\\.\\d{2})?";
    private static final String GENERIC_CURRENCY_SYMBOL = "\\p{Sc}";

    public static String currencyInputMatcher(String content, String currencySymbol) {
        String regex = currencySymbol.length() > 1 ?
            String.format("%2$s\\s*%1$s", currencySymbol, NUMERIC_REGEX) :
            String.format("%s\\s*%s", GENERIC_CURRENCY_SYMBOL, NUMERIC_REGEX);
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "";
    }

    public static Number validateCurrencyAmount(Locale locale, String input) {
        try {
            return NumberFormat.getCurrencyInstance(locale)
                .parse(input);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

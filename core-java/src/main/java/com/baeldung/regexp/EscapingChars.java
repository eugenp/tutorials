package com.baeldung.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EscapingChars {
    public boolean isMatching(String input, String pattern) {
        return input.matches(pattern);
    }

    public int splitAndCountWords(String input, String pattern) {
        return input.split(pattern).length;
    }

    public int splitAndCountWordsUsingQuoteMethod(String input, String pattern) {
        return input.split(Pattern.quote(pattern)).length;
    }

    public String changeCurrencySymbol(String input, String pattern,
            String correctStr) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.replaceAll(correctStr);
    }
}

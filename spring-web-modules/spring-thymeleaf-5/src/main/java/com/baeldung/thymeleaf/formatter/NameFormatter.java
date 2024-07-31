package com.baeldung.thymeleaf.formatter;

import org.springframework.format.Formatter;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.util.Locale;

/**
 * 
 * Name formatter class that implements the Spring Formatter interface. 
 * Formats a name(String) and return the value with spaces replaced by commas.
 *
 */
public class NameFormatter implements Formatter<String> {

    @Override
    public String print(String input, Locale locale) {
        return formatName(input, locale);
    }

    @Override
    public String parse(String input, Locale locale) throws ParseException {
        return formatName(input, locale);
    }

    private String formatName(String input, Locale locale) {
        return StringUtils.replace(input, " ", ",");
    }
}

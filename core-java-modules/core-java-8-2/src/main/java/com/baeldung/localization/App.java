package com.baeldung.localization;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class App {

    /**
     * Runs all available formatter
     * @throws ParseException 
     */
    public static void main(String[] args) {
        List<Locale> locales = Arrays.asList(new Locale[] { Locale.UK, Locale.ITALY, Locale.FRANCE, Locale.forLanguageTag("pl-PL") });
        Localization.run(locales);
        JavaSEFormat.run(locales);
        ICUFormat.run(locales);
    }

}

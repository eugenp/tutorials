package com.baeldung.localization;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {

    public static String getLabel(Locale locale) {
        final ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString("label");
    }

    public static void run(List<Locale> locales) {
        locales.forEach(locale -> System.out.println(getLabel(locale)));
    }

}

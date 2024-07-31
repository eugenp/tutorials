package com.baeldung.localization;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class JavaSEFormat {

    public static String getLabel(Locale locale, Object[] data) {
        ResourceBundle bundle = ResourceBundle.getBundle("formats", locale);
        final String pattern = bundle.getString("label");
        final MessageFormat formatter = new MessageFormat(pattern, locale);
        return formatter.format(data);
    }

    public static void run(List<Locale> locales) {
        System.out.println("Java formatter");
        final Date date = new Date(System.currentTimeMillis());
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { date, "Alice", 0 })));
        locales.forEach(locale -> System.out.println(getLabel(locale, new Object[] { date, "Alice", 2 })));
    }
}

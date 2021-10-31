package com.baeldung.utilities;

import java.util.Locale;

public final class GreetingsUtils {

    public static String sayHello(String name, Locale locale) {
        if ("hr".equals(locale.getLanguage())) {
            return String.format("Pozdrav %s!", name);
        } else {
            return String.format("Hello %s!", name);
        }
    }

    public static String sayHello(String name, String surname, Locale locale) {
        if ("hr".equals(locale.getLanguage())) {
            return String.format("Pozdrav %s %s!", name, surname);
        } else {
            return String.format("Hello %s %s!", name, surname);
        }
    }

}

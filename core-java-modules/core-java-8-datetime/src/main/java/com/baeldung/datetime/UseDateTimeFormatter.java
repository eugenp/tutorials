package com.baeldung.datetime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class UseDateTimeFormatter {
    public String formatAsIsoDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE);
    }

    public String formatCustom(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String formatWithStyleAndLocale(LocalDateTime localDateTime, FormatStyle formatStyle, Locale locale) {
        return localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(formatStyle)
            .withLocale(locale));
    }
}

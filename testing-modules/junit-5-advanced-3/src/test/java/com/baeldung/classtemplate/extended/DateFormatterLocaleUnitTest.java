package com.baeldung.classtemplate.extended;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.junit.jupiter.api.ClassTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassTemplate
@ExtendWith(DateLocaleClassTemplateProvider.class)
class DateFormatterLocaleUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(DateFormatterLocaleUnitTest.class);

    private final DateFormatter formatter = new DateFormatter();

    @Test
    void givenDefaultLocale_whenFormattingDate_thenMatchesLocalizedOutput() {
        LocalDate date = LocalDate.of(2025, 9, 30);

        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            .withLocale(Locale.getDefault());

        String expected = date.format(expectedFormatter);
        String formatted = formatter.format(date);

        LOG.info("Locale: {}, Expected: {}, Formatted: {}", Locale.getDefault(), expected, formatted);

        assertEquals(expected, formatted);
    }

}

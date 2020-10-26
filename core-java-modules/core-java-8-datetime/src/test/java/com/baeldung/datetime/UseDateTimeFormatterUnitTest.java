package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.junit.Test;

public class UseDateTimeFormatterUnitTest {
    private final UseDateTimeFormatter subject = new UseDateTimeFormatter();
    private final LocalDateTime localDateTime = LocalDateTime.of(2015, Month.JANUARY, 25, 6, 30);

    @Test
    public void givenALocalDate_whenFormattingAsIso_thenPass() {
        String result = subject.formatAsIsoDate(localDateTime);

        assertThat(result).isEqualTo("2015-01-25");
    }

    @Test
    public void givenALocalDate_whenFormattingWithPattern_thenPass() {
        String result = subject.formatCustom(localDateTime, "yyyy/MM/dd");

        assertThat(result).isEqualTo("2015/01/25");
    }

    @Test
    public void givenALocalDate_whenFormattingWithStyleAndLocale_thenPass() {
        String result = subject.formatWithStyleAndLocale(localDateTime, FormatStyle.MEDIUM, Locale.UK);

        assertThat(result).isEqualTo("25-Jan-2015 06:30:00");
    }
}
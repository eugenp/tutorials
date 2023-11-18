package com.baeldung.parsingDates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;
import java.util.Arrays;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

class SimpleParseDateUnitTest {

    @Test
    void whenInvalidInput_thenGettingUnexpectedResult() {
        String date = "2022-40-40";
        assertEquals("Sat May 10 00:00:00 UTC 2025", SimpleParseDate.parseDate(date, Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd"))
            .toString());
    }

    @Test
    void whenInvalidDate_thenAssertThrows() {
        assertEquals(java.time.LocalDate.parse("2022-12-04"), SimpleDateTimeFormater.parseDate("2022-12-04"));
        assertThrows(DateTimeParseException.class, () -> SimpleDateTimeFormater.parseDate("2022-13-04"));
    }

    @Test
    void whenDateIsCorrect_thenParseCorrect() {
        assertNull(SimpleDateUtils.parseDate("53/10/2014"));
        assertEquals("Wed Sep 10 00:00:00 UTC 2014", SimpleDateUtils.parseDate("10/09/2014")
            .toString());
    }

    @Test
    void whenDateIsCorrect_thenResultCorrect() {
        assertNull(SimpleDateTimeFormat.parseDate("53/10/2014"));
        assertEquals(LocalDate.parse("2014-10-10"), SimpleDateTimeFormat.parseDate("2014-10-10"));
    }

}

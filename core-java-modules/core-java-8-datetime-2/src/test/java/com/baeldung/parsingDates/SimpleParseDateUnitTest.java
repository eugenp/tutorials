package com.baeldung.parsingDates;

import com.baeldung.parsingDates.SimpleDateTimeFormat;
import com.baeldung.parsingDates.SimpleDateTimeFormater;
import com.baeldung.parsingDates.SimpleDateUtils;
import com.baeldung.parsingDates.SimpleParseDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;
import org.joda.time.LocalDate;

public class SimpleParseDateUnitTest {

    @Test
    public void whenInvalidInput_thenGettingUnexpectedResult() {
        SimpleParseDate simpleParseDate = new SimpleParseDate();
        String date = "2022-40-40";
        assertEquals("Sat May 10 00:00:00 UTC 2025", simpleParseDate.parseDate(date, Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd")).toString());
    }

    @Test
    public void whenInvalidDate_thenAssertThrows() {
        SimpleDateTimeFormater simpleDateTimeFormater = new SimpleDateTimeFormater();
        assertEquals(java.time.LocalDate.parse("2022-12-04"), simpleDateTimeFormater.parseDate("2022-12-04"));
        assertThrows(DateTimeParseException.class, () -> simpleDateTimeFormater.parseDate("2022-13-04"));
    }

    @Test
    public void whenDateIsCorrect_thenParseCorrect() {
        SimpleDateUtils simpleDateUtils = new SimpleDateUtils();
        assertNull(simpleDateUtils.parseDate("53/10/2014"));
        assertEquals("Wed Sep 10 00:00:00 UTC 2014", simpleDateUtils.parseDate("10/09/2014").toString());
    }

    @Test
    public void whenDateIsCorrect_thenResultCorrect() {
        SimpleDateTimeFormat simpleDateTimeFormat = new SimpleDateTimeFormat();
        assertNull(simpleDateTimeFormat.parseDate("53/10/2014"));
        assertEquals(LocalDate.parse("2014-10-10"), simpleDateTimeFormat.parseDate("2014-10-10"));
    }

}

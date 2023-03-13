package com.baeldung.parsingDates;

import com.baeldung.parsingDates.SimpleDateTimeFormat;
import com.baeldung.parsingDates.SimpleDateTimeFormater;
import com.baeldung.parsingDates.SimpleDateUtils;
import com.baeldung.parsingDates.SimpleParseDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

public class SimpleParseDateUnitTest {

    @Test
    public void testDateParse() {
        SimpleParseDate simpleParseDate = new SimpleParseDate();
        String date = "2022-40-40";
        assertEquals(simpleParseDate.parseDate(date, Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd")), "Sat May 10 00:00:00 UTC 2025");
    }

    @Test
    public void testSimpleDateTimeParse() {
        SimpleDateTimeFormater simpleDateTimeFormater = new SimpleDateTimeFormater();
        assertEquals(simpleDateTimeFormater.parseDate("2022-12-04"), "2022-12-04");
        assertThrows(DateTimeParseException.class, () -> simpleDateTimeFormater.parseDate("2022-13-04"));
    }

    @Test
    public void testDateUtils() {
        SimpleDateUtils simpleDateUtils = new SimpleDateUtils();
        assertNull(simpleDateUtils.parseDate("53/10/2014"));
        assertEquals(simpleDateUtils.parseDate("10/09/2014"), "10/09/2014");
    }

    @Test
    public void testJodaTime() {
        SimpleDateTimeFormat simpleDateUtils = new SimpleDateTimeFormat();
        assertNull(simpleDateUtils.parseDate("53/10/2014"));
        assertNotNull(simpleDateUtils.parseDate("10/10/2014"));
    }

}

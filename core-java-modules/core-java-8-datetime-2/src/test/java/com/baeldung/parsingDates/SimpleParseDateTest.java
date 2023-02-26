package com.baeldung.parsingDates;

import com.baeldung.parsingDates.SimpleDateTimeFormat;
import com.baeldung.parsingDates.SimpleDateTimeFormater;
import com.baeldung.parsingDates.SimpleDateUtils;
import com.baeldung.parsingDates.SimpleParseDate;
import java.util.Arrays;
import org.junit.*;

public class SimpleParseDateTest {

    @Test
    public void testDateParse() {
        SimpleParseDate simpleParseDate = new SimpleParseDate();
        String date = "2022-40-40";
        Assert.assertNotEquals(simpleParseDate.parseDate(date, Arrays.asList("MM/dd/yyyy", "dd.MM.yyyy", "yyyy-MM-dd")), null);
    }

    @Test
    public void testSimpleDateTimeParse() {
        SimpleDateTimeFormater simpleDateTimeFormater = new SimpleDateTimeFormater();
        Assert.assertNotNull(simpleDateTimeFormater.parseDate("2022-12-04"), "2022-12-04");
        Assert.assertNull(simpleDateTimeFormater.parseDate("2022-13-04"));
    }

    @Test
    public void testDateUtils() {
        SimpleDateUtils simpleDateUtils = new SimpleDateUtils();
        Assert.assertNull(simpleDateUtils.parseDate("53/10/2014"));
        Assert.assertNotNull(simpleDateUtils.parseDate("10/10/2014"));
    }

    @Test
    public void testJodaTime() {
        SimpleDateTimeFormat simpleDateUtils = new SimpleDateTimeFormat();
        Assert.assertNull(simpleDateUtils.parseDate("53/10/2014"));
        Assert.assertNotNull(simpleDateUtils.parseDate("10/10/2014"));
    }

}

package com.baeldung.datetime;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class TimeStampExtractYearMonthDayIntegerValuesUnitTest {

    Timestamp timestamp;

    @Before
    public void setup() throws ParseException {
        timestamp = new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse("20/05/2024")
            .getTime());
    }

    @Test
    public void whenGetDay_thenCorrectDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        assertEquals(20, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void whenGetMonth_thenCorrectMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        assertEquals(Calendar.MAY, calendar.get(Calendar.MONTH));
    }

    @Test
    public void whenGetYear_thenCorrectYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timestamp);

        assertEquals(2024, calendar.get(Calendar.YEAR));
    }
}
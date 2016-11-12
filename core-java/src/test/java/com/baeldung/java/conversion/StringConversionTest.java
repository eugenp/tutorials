package com.baeldung.java.conversion;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.baeldung.datetime.UseLocalDateTime;

public class StringConversionTest {

    @Test
    public void whenConvertedToInt_thenCorrect() {
        assertEquals(Integer.parseInt("1"), 1);
    }

    @Test
    public void whenConvertedToInteger_thenCorrect() {
        assertEquals(Integer.valueOf("12").equals(12), true);
    }

    @Test
    public void whenConvertedTolong_thenCorrect() {
        assertEquals(Long.parseLong("12345"), 12345);
    }

    @Test
    public void whenConvertedToLong_thenCorrect() {
        assertEquals(Long.valueOf("14567").equals(14567L), true);
    }

    @Test
    public void whenConvertedTodouble_thenCorrect() {
        assertEquals(Double.parseDouble("1.4"), 1.4, 0.0);
    }

    @Test
    public void whenConvertedToDouble_thenCorrect() {
        assertEquals(Double.valueOf("145.67").equals(145.67d), true);
    }

    @Test
    public void whenConvertedToByteArray_thenCorrect() throws UnsupportedEncodingException {
        byte[] byteArray1 = new byte[] { 'a', 'b', 'c' };
        String string = new String(byteArray1, "UTF-8");

        assertEquals(Arrays.equals(string.getBytes(), byteArray1), true);
    }

    @Test
    public void whenConvertedToboolean_thenCorrect() {
        assertEquals(Boolean.parseBoolean("true"), true);
    }

    @Test
    public void whenConvertedToBoolean_thenCorrect() {
        assertEquals(Boolean.valueOf("true"), true);
    }

    @Test
    public void whenConvertedToCharArray_thenCorrect() {
        String str = "hello";
        char[] charArray = { 'h', 'e', 'l', 'l', 'o' };

        assertEquals(Arrays.equals(charArray, str.toCharArray()), true);
    }

    @Test
    public void whenConvertedToDate_thenCorrect() throws ParseException {
        String str = "15/10/2013";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
        Date date1 = formatter.parse(str);
        Calendar calendar = new GregorianCalendar(2013, 9, 15);
        Date date2 = calendar.getTime();

        assertEquals(date1.compareTo(date2), 0);
    }

    @Test
    public void whenConvertedToLocalDateTime_thenCorrect() throws ParseException {
        String str = "2007-12-03T10:15:30";
        LocalDateTime localDateTime = new UseLocalDateTime().getLocalDateTimeUsingParseMethod(str);

        assertEquals(localDateTime.getDayOfMonth(), 3);
        assertEquals(localDateTime.getMonth(), Month.DECEMBER);
        assertEquals(localDateTime.getYear(), 2007);
    }
}

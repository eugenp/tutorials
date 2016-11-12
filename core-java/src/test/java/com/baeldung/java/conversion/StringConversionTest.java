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
    public void givenString_whenConvertedToint_thenCorrect() {
        String str = "1";
        assertEquals(Integer.parseInt(str), 1);
    }

    @Test
    public void givenString_whenConvertedToInteger_thenCorrect() {
        String str = "12";
        Integer number = Integer.valueOf(str);
        assertEquals(number.intValue(), 12);
    }

    @Test
    public void givenString_whenConvertedTolong_thenCorrect() {
        String str = "12345";
        assertEquals(Long.parseLong(str), 12345);
    }

    @Test
    public void givenString_whenConvertedToLong_thenCorrect() {
        String str = "14567";
        Long number = Long.valueOf(str);
        assertEquals(number.longValue(), 14567);
    }

    @Test
    public void givenString_whenConvertedTodouble_thenCorrect() {
        String str = "1.4";
        assertEquals(Double.parseDouble(str), 1.4, 0.0);
    }

    @Test
    public void givenString_whenConvertedToDouble_thenCorrect() {
        String str = "145.67";
        Double number = Double.valueOf(str);
        assertEquals(number.doubleValue(), 145.67, 0.0);
    }

    @Test
    public void givenString_whenConvertedToByteArray_thenCorrect() throws UnsupportedEncodingException {
        byte[] byteArray1 = new byte[] { 'a', 'b', 'c' };
        String str = new String(byteArray1, "UTF-8");
        assertEquals(Arrays.equals(str.getBytes(), byteArray1), true);
    }

    @Test
    public void givenString_whenConvertedToboolean_thenCorrect() {
        String str = "true";
        assertEquals(Boolean.parseBoolean(str), true);
    }

    @Test
    public void givenString_whenConvertedToBoolean_thenCorrect() {
        String str = "true";
        assertEquals(Boolean.valueOf(str), true);
    }

    @Test
    public void givenString_whenConvertedToCharArray_thenCorrect() {
        String str = "hello";
        char[] charArray = { 'h', 'e', 'l', 'l', 'o' };
        assertEquals(Arrays.equals(charArray, str.toCharArray()), true);
    }

    @Test
    public void givenString_whenConvertedToJava6Date_thenCorrect() throws ParseException {
        String str = "15/10/2013";
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
        Date date1 = formatter.parse(str);
        Calendar calendar = new GregorianCalendar(2013, 9, 15);
        Date date2 = calendar.getTime();
        assertEquals(date1.compareTo(date2), 0);
    }

    @Test
    public void givenString_whenConvertedToJava8Date_thenCorrect() throws ParseException {
        String str = "2007-12-03T10:15:30";
        LocalDateTime localDateTime = new UseLocalDateTime().getLocalDateTimeUsingParseMethod(str);
        assertEquals(localDateTime.getDayOfMonth(), 3);
        assertEquals(localDateTime.getMonth(), Month.DECEMBER);
        assertEquals(localDateTime.getYear(), 2007);
    }
}

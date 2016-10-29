package org.baeldung.java;

import org.assertj.core.internal.cglib.core.Local;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


public class JavaStringConversionUnitTest {
    @Test
    public void givenString_whenConvertingToPrimitiveBoolean_thenCorrect() {
        boolean primitiveBoolean = Boolean.parseBoolean("true");

        Assert.assertTrue(primitiveBoolean);
    }

    @Test
    public void givenString_whenConvertingToWrapperBoolean_thenCorrect() {
        Boolean wrapperBoolean = Boolean.valueOf("false");

        Assert.assertFalse(wrapperBoolean);
    }

    @Test
    public void givenString_whenConvertingToPrimitiveByte_thenCorrect() {
        byte primitiveByte = Byte.parseByte("1");

        assertThat(primitiveByte == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToWrapperByte_thenCorrect() {
        Byte wrapperByte = Byte.valueOf("1");

        assertThat(wrapperByte == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveShort_thenCorrect() {
        short primitiveShort = Short.parseShort("1");

        assertThat(primitiveShort == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToWrapperShort_thenCorrect() {
        Short wrapperShort = Short.valueOf("1");

        assertThat(wrapperShort == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveInt_thenCorrect() {
        int primitiveInt = Integer.parseInt("1");

        assertThat(primitiveInt == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToWrapperInteger_thenCorrect() {
        Integer wrapperInt = Integer.valueOf("1");

        assertThat(wrapperInt == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveLong_thenCorrect() {
        long primitiveLong = Long.parseLong("1");

        assertThat(primitiveLong == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToWrapperLong_thenCorrect() {
        Long wrapperLong = Long.valueOf("1");

        assertThat(wrapperLong == 1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveFloat_thenCorrect() {
        float primitiveFloat = Float.parseFloat("1.1");

        assertThat(primitiveFloat == 1.1F).isTrue();
    }

    @Test
    public void givenString_whenConvertingToWrapperFloat_thenCorrect() {
        Float wrapperFloat = Float.valueOf("1.1");

        assertThat(wrapperFloat == 1.1F).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveDouble_thenCorrect() {
        double primitiveDouble = Double.parseDouble("1.1");

        assertThat(primitiveDouble == 1.1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToWrapperDouble_thenCorrect() {
        Double wrapperDouble = Double.valueOf("1.1");

        assertThat(wrapperDouble == 1.1).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveChar_thenCorrect() {
        String primitiveChar = "C";
        char h = primitiveChar.charAt(0);

        assertThat(h == 'C').isTrue();
    }


    @Test
    public void givenString_whenConvertingToPrimitiveCharArray_thenCorrect() {
        String primitiveChars = "Hello world";
        char[] chars = primitiveChars.toCharArray();

        assertThat(Arrays.equals(chars,new char[]{'H','e', 'l', 'l', 'o', ' ', 'w', 'o', 'r', 'l', 'd'})).isTrue();
    }

    @Test
    public void givenString_whenConvertingToPrimitiveByteArray_default_thenCorrect() {
        String primitiveBytes = "Hello world";
        byte[] bytes = primitiveBytes.getBytes();

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void givenString_whenConvertingToPrimitiveByteArray_CharsetName_thenCorrect() throws UnsupportedEncodingException {
        String primitiveBytes = "Hello world";
        byte[] bytes = primitiveBytes.getBytes("UTF-8");

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void givenString_whenConvertingToPrimitiveByteArray_Charset_thenCorrect() {
        String primitiveBytes = "Hello world";
        byte[] bytes = primitiveBytes.getBytes(StandardCharsets.UTF_8);

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void givenString_whenConvertingToDate_java7_thenCorrect() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "26/06/2016";
        Date date = formatter.parse(dateInString);

        assertThat(date.equals(Date.from(LocalDate.of(2016,06,26).atStartOfDay(ZoneId.systemDefault()).toInstant()))).isTrue();
    }

    @Test
    public void givenString_whenConvertingToDate_java8_thenCorrect() {
        LocalDate localDate = LocalDate.parse("2016-06-26", DateTimeFormatter.ISO_LOCAL_DATE);

        assertThat(localDate.equals(LocalDate.of(2016,06,26))).isTrue();
    }

    @Test
    public void givenDate_whenConvertingToString_java7_thenCorrect() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateToString = formatter.format(Date.from(LocalDate.of(2016,06,26).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertThat(dateToString.equals("26/06/2016")).isTrue();
    }

    @Test
    public void givenDate_whenConvertingToString_java8_thenCorrect() {
        LocalDate localDate = LocalDate.of(2016,06,26);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateToString = dateTimeFormatter.format(localDate);

        assertThat(dateToString.equals("2016-06-26")).isTrue();
    }
}

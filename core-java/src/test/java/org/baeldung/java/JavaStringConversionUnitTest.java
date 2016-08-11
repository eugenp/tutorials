package org.baeldung.java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by sic.org on 8/2/2016.
 */
public class JavaStringConversionUnitTest {
    @Test
    public void given_StringToboolean_thenCorrect() {
        boolean stringToBoolean = Boolean.parseBoolean("true");

        System.out.println(stringToBoolean);
    }

    @Test
    public void given_StringToBoolean_thenCorrect() {
        Boolean stringToBoolean = Boolean.valueOf("false");

        System.out.println(stringToBoolean);
    }

    @Test
    public void given_StringTobyte_thenCorrect() {
        byte stringToByte = Byte.parseByte("1");

        System.out.println(stringToByte);
    }

    @Test
    public void given_StringToByte_thenCorrect() {
        Byte stringToByte = Byte.valueOf("1");

        System.out.println(stringToByte);
    }

    @Test
    public void given_StringToshort_thenCorrect() {
        short stringToShort = Short.parseShort("1");

        System.out.println(stringToShort);
    }

    @Test
    public void given_StringToShort_thenCorrect() {
        Short stringToShort = Short.valueOf("1");

        System.out.println(stringToShort);
    }

    @Test
    public void given_StringToint_thenCorrect() {
        int stringToInt = Integer.parseInt("1");

        System.out.println(stringToInt);
    }

    @Test
    public void given_StringToInteger_thenCorrect() {
        Integer stringToInt = Integer.valueOf("1");

        System.out.println(stringToInt);
    }

    @Test
    public void given_StringTolong_thenCorrect() {
        long stringToLong = Long.parseLong("1");

        System.out.println(stringToLong);
    }

    @Test
    public void given_StringToLong_thenCorrect() {
        Long stringToLong = Long.valueOf("1");

        System.out.println(stringToLong);
    }

    @Test
    public void given_StringTofloat_thenCorrect() {
        float stringToFloat = Float.parseFloat("1.1");

        System.out.println(stringToFloat);
    }

    @Test
    public void given_StringToFloat_thenCorrect() {
        Float stringToFloat = Float.valueOf("1.1");

        System.out.println(stringToFloat);
    }

    @Test
    public void given_StringTodouble_thenCorrect() {
        double stringToDouble = Double.parseDouble("1");

        System.out.println(stringToDouble);
    }

    @Test
    public void given_StringToDouble_thenCorrect() {
        Double stringToDouble = Double.valueOf("1.1");

        System.out.println(stringToDouble);
    }

    @Test
    public void given_StringTochar_thenCorrect() {
        String stringToChar = "C";
        char h = stringToChar.charAt(0);

        System.out.println(stringToChar);
    }


    @Test
    public void given_StringTocharArray_thenCorrect() {
        String stringToChars = "Hello world";
        char[] chars= stringToChars.toCharArray();

        System.out.println(Arrays.toString(chars));
    }

    @Test
    public void given_StringTobyteArray_default_thenCorrect() {
        String strToBytes = "Hello world";
        byte[] bytes = strToBytes.getBytes();

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void given_StringTobyteArray_CharsetName_thenCorrect() throws UnsupportedEncodingException {
        String stringToBytes = "Hello world";
        byte[] bytes = stringToBytes.getBytes("UTF-8");

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void given_StringTobyteArray_Charset_thenCorrect() {
        String stringToBytes = "Hello world";
        byte[] bytes = stringToBytes.getBytes(StandardCharsets.UTF_8);

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void given_java7String_To_Date_thenCorrect() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "05/30/2016";
        Date date = formatter.parse(dateInString);

        System.out.println(date);
    }

    @Test
    public void given_java8String_To_Date_thenCorrect() {
        LocalDate localDate = LocalDate.parse("2016-06-26", DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.println(localDate);
    }

    @Test
    public void given_java7DateToString_thenCorrect() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateToString = formatter.format(new Date());

        System.out.println(dateToString);
    }

    @Test
    public void given_java8DateToString_thenCorrect() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateToString = dateTimeFormatter.format(localDate);//2016-06-26

        System.out.println(dateToString);
    }
}

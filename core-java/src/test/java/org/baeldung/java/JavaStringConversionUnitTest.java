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
    public void stringToboolean() {
        boolean stringToBoolean = Boolean.parseBoolean("true");

        System.out.println(stringToBoolean);
    }

    @Test
    public void stringToBoolean() {
        Boolean stringToBoolean = Boolean.valueOf("false");

        System.out.println(stringToBoolean);
    }

    @Test
    public void stringTobyte() {
        byte stringToByte = Byte.parseByte("1");

        System.out.println(stringToByte);
    }

    @Test
    public void stringToByte() {
        Byte stringToByte = Byte.valueOf("1");

        System.out.println(stringToByte);
    }

    @Test
    public void stringToshort() {
        short stringToShort = Short.parseShort("1");

        System.out.println(stringToShort);
    }

    @Test
    public void stringToShort() {
        Short stringToShort = Short.valueOf("1");

        System.out.println(stringToShort);
    }

    @Test
    public void stringToint() {
        int stringToInt = Integer.parseInt("1");

        System.out.println(stringToInt);
    }

    @Test
    public void stringToInteger() {
        Integer stringToInt = Integer.valueOf("1");

        System.out.println(stringToInt);
    }

    @Test
    public void stringTolong() {
        long stringToLong = Long.parseLong("1");

        System.out.println(stringToLong);
    }

    @Test
    public void stringToLong() {
        Long stringToLong = Long.valueOf("1");

        System.out.println(stringToLong);
    }

    @Test
    public void stringTofloat() {
        float stringToFloat = Float.parseFloat("1.1");

        System.out.println(stringToFloat);
    }

    @Test
    public void stringToFloat() {
        Float stringToFloat = Float.valueOf("1.1");

        System.out.println(stringToFloat);
    }

    @Test
    public void stringTodouble() {
        double stringToDouble = Double.parseDouble("1");

        System.out.println(stringToDouble);
    }

    @Test
    public void stringToDouble() {
        Double stringToDouble = Double.valueOf("1.1");

        System.out.println(stringToDouble);
    }

    @Test
    public void stringTochar() {
        String stringToChar = "C";
        char h = stringToChar.charAt(0);

        System.out.println(stringToChar);
    }


    @Test
    public void stringTocharArray() {
        String stringToChars = "Hello world";
        char[] chars = stringToChars.toCharArray();

        System.out.println(Arrays.toString(chars));
    }

    @Test
    public void stringTobyteArray_default() {
        String strToBytes = "Hello world";
        byte[] bytes = strToBytes.getBytes();

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void stringTobyteArray_CharsetName() throws UnsupportedEncodingException {
        String stringToBytes = "Hello world";
        byte[] bytes = stringToBytes.getBytes("UTF-8");

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void stringTobyteArray_Charset() {
        String stringToBytes = "Hello world";
        byte[] bytes = stringToBytes.getBytes(StandardCharsets.UTF_8);

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void java7StringToDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "05/30/2016";
        Date date = formatter.parse(dateInString);

        System.out.println(date);
    }

    @Test
    public void java8StringToDate() {
        LocalDate localDate = LocalDate.parse("2016-06-26", DateTimeFormatter.ISO_LOCAL_DATE);

        System.out.println(localDate);
    }

    @Test
    public void java7DateToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateToString = formatter.format(new Date());

        System.out.println(dateToString);
    }

    @Test
    public void java8DateToString() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String dateToString = dateTimeFormatter.format(localDate);//2016-06-26

        System.out.println(dateToString);
    }
}

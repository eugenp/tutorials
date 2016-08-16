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



public class JavaStringConversionUnitTest {
    @Test
    public void stringToPrimitiveBoolean() {
        boolean primitiveBoolean = Boolean.parseBoolean("true");

        System.out.println(primitiveBoolean);
    }

    @Test
    public void stringToWrapperBoolean() {
        Boolean wrapperBoolean = Boolean.valueOf("false");

        System.out.println(wrapperBoolean);
    }

    @Test
    public void stringToPrimitiveByte() {
        byte primitiveByte = Byte.parseByte("1");

        System.out.println(primitiveByte);
    }

    @Test
    public void stringToWrapperByte() {
        Byte wrapperByte = Byte.valueOf("1");

        System.out.println(wrapperByte);
    }

    @Test
    public void stringToPrimitiveShort() {
        short primitiveShort = Short.parseShort("1");

        System.out.println(primitiveShort);
    }

    @Test
    public void stringToWrapperShort() {
        Short wrapperShort = Short.valueOf("1");

        System.out.println(wrapperShort);
    }

    @Test
    public void stringToPrimitiveInt() {
        int primitiveInt = Integer.parseInt("1");

        System.out.println(primitiveInt);
    }

    @Test
    public void stringToWrapperInteger() {
        Integer wrapperInt = Integer.valueOf("1");

        System.out.println(wrapperInt);
    }

    @Test
    public void stringToPrimitiveLong() {
        long primitiveLong = Long.parseLong("1");

        System.out.println(primitiveLong);
    }

    @Test
    public void stringToWrapperLong() {
        Long wrapperLong = Long.valueOf("1");

        System.out.println(wrapperLong);
    }

    @Test
    public void stringToPrimitiveFloat() {
        float primitiveFloat = Float.parseFloat("1.1");

        System.out.println(primitiveFloat);
    }

    @Test
    public void stringToWrapperFloat() {
        Float wrapperFloat = Float.valueOf("1.1");

        System.out.println(wrapperFloat);
    }

    @Test
    public void stringToPrimitiveDouble() {
        double primitiveDouble = Double.parseDouble("1.1");

        System.out.println(primitiveDouble);
    }

    @Test
    public void stringToWrapperDouble() {
        Double wrapperDouble = Double.valueOf("1.1");

        System.out.println(wrapperDouble);
    }

    @Test
    public void stringToPrimitiveChar() {
        String primitiveChar = "C";
        char h = primitiveChar.charAt(0);

        System.out.println(h);
    }


    @Test
    public void stringToPrimitiveCharArray() {
        String primitiveChars = "Hello world";
        char[] chars = primitiveChars.toCharArray();

        System.out.println(Arrays.toString(chars));
    }

    @Test
    public void stringToPrimitiveByteArray_default() {
        String primitiveBytes = "Hello world";
        byte[] bytes = primitiveBytes.getBytes();

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void stringToPrimitiveByteArray_CharsetName() throws UnsupportedEncodingException {
        String primitiveBytes = "Hello world";
        byte[] bytes = primitiveBytes.getBytes("UTF-8");

        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void stringToPrimitiveByteArray_Charset() {
        String primitiveBytes = "Hello world";
        byte[] bytes = primitiveBytes.getBytes(StandardCharsets.UTF_8);

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

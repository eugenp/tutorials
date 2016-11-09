package com.baeldung.java.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.baeldung.datetime.UseLocalDateTime;

public class StringConversion {

    public static int getInt(String str) {
        return Integer.parseInt(str);
    }

    public static int getInteger(String str) {
        return Integer.valueOf(str);
    }

    public static long getLongPrimitive(String str) {
        return Long.parseLong(str);
    }

    public static Long getLong(String str) {
        return Long.valueOf(str);
    }

    public static double getDouble(String str) {
        return Double.parseDouble(str);
    }

    public static double getDoublePrimitive(String str) {
        return Double.valueOf(str);
    }

    public static byte[] getByteArray(String str) {
        return str.getBytes();
    }

    public static char[] getCharArray(String str) {
        return str.toCharArray();
    }

    public static boolean getBooleanPrimitive(String str) {
        return Boolean.parseBoolean(str);
    }

    public static boolean getBoolean(String str) {
        return Boolean.valueOf(str);
    }

    public static Date getJava6Date(String str, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(str);
    }

    public static LocalDateTime getJava8Date(String str) throws ParseException {
        return new UseLocalDateTime().getLocalDateTimeUsingParseMethod(str);
    }
}

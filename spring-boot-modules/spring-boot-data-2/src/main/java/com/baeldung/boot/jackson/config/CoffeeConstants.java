package com.baeldung.boot.jackson.config;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class CoffeeConstants {

    public static final String dateTimeFormat = "dd-MM-yyyy HH:mm";
    public static LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat));
}

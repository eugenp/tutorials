package com.baeldung.boot.jackson.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.format.DateTimeFormatter;

public class CoffeeConstants {

    public static final String dateTimeFormat = "dd-MM-yyyy HH:mm";
    public static LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat));
}

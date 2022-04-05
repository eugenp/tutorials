package com.baeldung.boot.jackson.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CoffeeConstants {

    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final LocalDateTime FIXED_DATE = LocalDateTime.now();
    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
}

package com.baeldung.jcommander.usagebilling.cli.converter;

import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.BaseConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;

public class ISO8601TimestampConverter extends BaseConverter<Instant> {

    private static final DateTimeFormatter TS_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

    public ISO8601TimestampConverter(String optionName) {
        super(optionName);
    }

    @Override
    public Instant convert(String value) {
        try {
            return LocalDateTime
              .parse(value, TS_FORMATTER)
              .atOffset(ZoneOffset.UTC)
              .toInstant();
        } catch (DateTimeParseException e) {
            throw new ParameterException(getErrorString(value, format("an ISO-8601 formatted timestamp (%s)", TS_FORMATTER.toString())));
        }
    }
}

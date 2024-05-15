package com.baeldung.bsontojson;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.bson.json.Converter;
import org.bson.json.StrictJsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDateTimeConverter implements Converter<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDateTimeConverter.class);
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_INSTANT
        .withZone(ZoneId.of("UTC"));

    @Override
    public void convert(Long value, StrictJsonWriter writer) {
        try {
            Instant instant = new Date(value).toInstant();
            String s = DATE_TIME_FORMATTER.format(instant);
            writer.writeString(s);
        } catch (Exception e) {
            LOGGER.error(String.format("Fail to convert offset %d to JSON date", value), e);
        }
    }
}

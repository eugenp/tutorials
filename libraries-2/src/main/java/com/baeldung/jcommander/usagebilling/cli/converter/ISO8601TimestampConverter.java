package com.baeldung.jcommander.usagebilling.cli.converter;

import com.beust.jcommander.IStringConverter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ISO8601TimestampConverter implements IStringConverter<Instant> {

  @Override
  public Instant convert(String value) {
    return LocalDateTime
        .parse(value, DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"))
        .atOffset(ZoneOffset.UTC)
        .toInstant();
  }
}

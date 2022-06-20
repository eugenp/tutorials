package com.gateway.config.cassandra;

import com.datastax.driver.core.*;
import com.datastax.driver.core.exceptions.InvalidTypeException;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import static com.datastax.driver.core.ParseUtils.isLongLiteral;
import static com.datastax.driver.core.ParseUtils.quote;
import static java.time.temporal.ChronoField.*;

public class CustomZonedDateTimeCodec extends TypeCodec<ZonedDateTime> {

    public static final CustomZonedDateTimeCodec instance = new CustomZonedDateTimeCodec();

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseSensitive()
            .parseStrict()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .optionalStart()
            .appendLiteral('T')
            .appendValue(HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .optionalEnd()
            .optionalStart()
            .appendLiteral(':')
            .appendValue(SECOND_OF_MINUTE, 2)
            .optionalEnd()
            .optionalStart()
            .appendFraction(NANO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .optionalStart()
            .appendZoneOrOffsetId()
            .optionalEnd()
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    private CustomZonedDateTimeCodec() {
        super(DataType.timestamp(), ZonedDateTime.class);
    }

    @Override
    public ByteBuffer serialize(ZonedDateTime value, ProtocolVersion protocolVersion) {
        if (value == null) {
            return null;
        }
        long millis = value.toInstant().toEpochMilli();
        return bigint().serializeNoBoxing(millis, protocolVersion);
    }

    @Override
    public ZonedDateTime deserialize(ByteBuffer bytes, ProtocolVersion protocolVersion) {
        if (bytes == null || bytes.remaining() == 0) {
            return null;
        }
        long millis = bigint().deserializeNoBoxing(bytes, protocolVersion);
        return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC);
    }

    @Override
    public String format(ZonedDateTime value) {
        return quote(FORMATTER.format(value));
    }

    @Override
    public ZonedDateTime parse(String value) {
            // strip enclosing single quotes, if any
            if (ParseUtils.isQuoted(value)) {
                value = ParseUtils.unquote(value);
            }
            if (isLongLiteral(value)) {
                try {
                    long millis = Long.parseLong(value);
                    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
                } catch (NumberFormatException e) {
                    throw new InvalidTypeException(String.format("Cannot parse timestamp value from \"%s\"", value));
                }
            }
            try {
                return ZonedDateTime.from(FORMATTER.parse(value));
            } catch (DateTimeParseException e) {
                throw new InvalidTypeException(String.format("Cannot parse timestamp value from \"%s\"", value));
            }
     }

}

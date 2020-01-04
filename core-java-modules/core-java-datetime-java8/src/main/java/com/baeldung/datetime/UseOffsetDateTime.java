package com.baeldung.datetime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class UseOffsetDateTime {
    public OffsetDateTime offsetOfLocalDateTimeAndOffset(LocalDateTime localDateTime, ZoneOffset offset) {
        return OffsetDateTime.of(localDateTime, offset);
    }
}

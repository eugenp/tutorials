package com.baeldung.offsetdatetime;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class ConvertToOffsetDateTime {

    public static OffsetDateTime convert(Date date) {
        return date.toInstant()
            .atOffset(ZoneOffset.UTC);
    }

    public static OffsetDateTime convert(Date date, int hour, int minute) {
        return date.toInstant()
            .atOffset(ZoneOffset.ofHoursMinutes(hour, minute));
    }

}

package com.baeldung.convertdateandzoneddatetime;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateAndZonedDateTimeConverter {

    public static Date convertToDate(ZonedDateTime zonedDateTime) {
        return Date.from(zonedDateTime.toInstant());
    }

    public static ZonedDateTime convertToZonedDateTime(Date date, ZoneId zone) {
        return date.toInstant().atZone(zone);
    }

}

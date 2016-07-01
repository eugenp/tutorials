package com.baeldung.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UseZonedDateTime {
    
    public ZonedDateTime getZonedDateTime(LocalDateTime localDateTime,ZoneId zoneId){        
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zoneId);
        return zonedDateTime;
    }
}

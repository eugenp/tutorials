package com.baeldung.protobuftimestamptolocaldate;

import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimestampToLocalDate {

    public static LocalDate convertToLocalDate(Timestamp timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        LocalDate time = instant.atZone(ZoneId.of("America/Montreal"))
            .toLocalDate();
        return time;

    }

}

package com.baeldung.protobuftimestamptolocaldate;

import com.google.protobuf.Timestamp;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TimestampToLocalDateUnitTest {

    @Test
    public void givenTimestamp_whenConvertedToLocalDate_thenSuccess() {
        Timestamp timestamp = Timestamp.newBuilder()
            .setSeconds(1000000000)
            .setNanos(778866000)
            .build();
        LocalDate time = TimestampToLocalDate.convertToLocalDate(timestamp);
        assertEquals(LocalDate.of(2001, 9, 8), time);
    }

}
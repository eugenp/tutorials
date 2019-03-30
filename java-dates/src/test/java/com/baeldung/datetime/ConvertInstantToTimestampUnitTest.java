package com.baeldung.datetime;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

import org.junit.Test;

public class ConvertInstantToTimestampUnitTest {

    @Test
    public void givenInstant_whenConvertedToTimestamp_thenGetTimestampWithSamePointOnTimeline() {
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant); // same point on the time-line as Instant
        assertThat(instant.toEpochMilli()).isEqualTo(timestamp.getTime());

        instant = timestamp.toInstant();
        assertThat(instant.toEpochMilli()).isEqualTo(timestamp.getTime());

        DateFormat df = DateFormat.getDateTimeInstance();
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        assertThat(instant.toString()).isEqualTo(df.format(timestamp).toString());
    }
}

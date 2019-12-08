package com.baeldung.datetime;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

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

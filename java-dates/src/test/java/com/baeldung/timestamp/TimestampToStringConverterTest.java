package com.baeldung.timestamp;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class TimestampToStringConverterTest {

    @Test
    public void givenDatePattern_whenFormatting_thenResultingStringIsCorrect() {
        String pattern = "yyyy/MM/dd HH-mm-ss.SSSSSSSSS";
        Timestamp timestamp = Timestamp.valueOf("2018-12-12 01:02:03.123456789");

        String timestampAsString = TimestampToStringConverter.convert(timestamp, pattern);
        Assert.assertEquals("2018/12/12 01-02-03.123456789", timestampAsString);
    }
}
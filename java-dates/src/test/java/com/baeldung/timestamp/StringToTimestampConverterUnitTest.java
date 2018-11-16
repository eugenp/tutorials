package com.baeldung.timestamp;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class StringToTimestampConverterUnitTest {

    @Test
    public void testParsing() {
        String pattern = "yyyy-MM-dd HH-mm-ss.SSSSSSSS";
        String timestampAsString = "2018-12-12 01-02-03.12345678";

        Timestamp timestamp =  StringToTimestampConverter.convert(timestampAsString, pattern);
        Assert.assertEquals("2018-12-12 01:02:03.12345678", timestamp.toString());
    }
}
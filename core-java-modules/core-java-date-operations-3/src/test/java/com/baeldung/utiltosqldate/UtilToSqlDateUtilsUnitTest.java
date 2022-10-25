package com.baeldung.utiltosqldate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class UtilToSqlDateUtilsUnitTest {

    @Test
    public void given_utilDate_when_Casting_to_sqldate_throw_exception() {
        Assertions.assertThrows(ClassCastException.class, () -> {
            java.sql.Date date = (java.sql.Date) new java.util.Date();
        });
    }

    @Test
    public void given_utilDate_whenStandardConversion_timezone_lost() throws ParseException {
        java.util.Date date = UnitToSqlDateUtils.createAmericanDate("2010-05-23T22:01:02");

        java.sql.Date sqlDate;

        UnitToSqlDateUtils.switchTimezone("America/Los_Angeles");
        sqlDate = new java.sql.Date(date.getTime());
        Assertions.assertEquals("2010-05-23", sqlDate.toString());

        UnitToSqlDateUtils.switchTimezone("Rome");
        sqlDate = new java.sql.Date(date.getTime());
        Assertions.assertEquals("2010-05-24",sqlDate.toString());
    }

    @Test
    public void given_utilDate_conversion_to_timestamp_keep_time_info() throws ParseException {
        java.util.Date date = UnitToSqlDateUtils.createAmericanDate("2010-05-23T22:01:02");
        UnitToSqlDateUtils.switchTimezone("America/Los_Angeles");
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        Assertions.assertEquals("2010-05-23 22:01:02.0",timestamp.toString());
    }

    @Test
    public void given_utilDate_whenUsingJavaTimeConversion_timezone_kept() throws ParseException {
        java.util.Date date = UnitToSqlDateUtils.createAmericanDate("2010-05-23T22:01:02");

        java.time.LocalDate localDate;

        UnitToSqlDateUtils.switchTimezone("America/Los_Angeles");
        localDate = UnitToSqlDateUtils.getLocalDate(date,"America/Los_Angeles");
        Assertions.assertEquals(localDate.toString(), "2010-05-23");

        UnitToSqlDateUtils.switchTimezone("Rome");
        localDate = UnitToSqlDateUtils.getLocalDate(date,"America/Los_Angeles");
        Assertions.assertEquals(localDate.toString(), "2010-05-23");
    }

}

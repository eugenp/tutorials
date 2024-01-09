package com.baeldung.utiltosqldate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;

public class UtilToSqlDateUtilsUnitTest {

    @Test
    public void givenUtilDate_whenCastingToSqlDate_thenThrowException() {
        Assertions.assertThrows(ClassCastException.class, () -> {
            java.sql.Date date = (java.sql.Date) new java.util.Date();
        });
    }

    @Test
    public void givenUtilDate_whenStandardConversion_thenTimezoneLost() throws ParseException {
        java.util.Date date = UtilToSqlDateUtils.createAmericanDate("2010-05-23T22:01:02");

        UtilToSqlDateUtils.switchTimezone("America/Los_Angeles");

        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Assertions.assertEquals("2010-05-23", sqlDate.toString());

        UtilToSqlDateUtils.switchTimezone("Rome");
        sqlDate = new java.sql.Date(date.getTime());
        Assertions.assertEquals("2010-05-24", sqlDate.toString());
    }

    @Test
    public void givenUtilDate_whenConversionToTimestamp_thenKeepTimeInfo() throws ParseException {
        java.util.Date date = UtilToSqlDateUtils.createAmericanDate("2010-05-23T22:01:02");
        UtilToSqlDateUtils.switchTimezone("America/Los_Angeles");
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        Assertions.assertEquals("2010-05-23 22:01:02.0", timestamp.toString());
    }

    @Test
    public void givenUtilDate_whenUsingJavaTimeConversion_thenTimezoneKept() throws ParseException {
        java.util.Date date = UtilToSqlDateUtils.createAmericanDate("2010-05-23T22:01:02");

        UtilToSqlDateUtils.switchTimezone("America/Los_Angeles");

        java.time.LocalDate localDate = UtilToSqlDateUtils.getLocalDate(date, "America/Los_Angeles");
        Assertions.assertEquals(localDate.toString(), "2010-05-23");

        UtilToSqlDateUtils.switchTimezone("Rome");
        localDate = UtilToSqlDateUtils.getLocalDate(date, "America/Los_Angeles");
        Assertions.assertEquals(localDate.toString(), "2010-05-23");
    }

    @Test
    public void givenSqlDateAndTimestamp_whenUsingAsUtilDate_thenGetExpectedResult() throws ParseException {
        java.util.Date date = UtilToSqlDateUtils.createAmericanDate("2010-05-23T00:00:00");
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        Assertions.assertEquals(date, sqlDate);

        java.util.Date dateWithTime = UtilToSqlDateUtils.createAmericanDate("2010-05-23T23:59:59");
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(dateWithTime.getTime());
        Assertions.assertEquals(dateWithTime, sqlTimestamp);
    }

    @Test
    public void givenSqlDateAndTimestamp_whenCreatingNewUtilDate_thenGetExpectedResult() throws ParseException {
        java.util.Date date = UtilToSqlDateUtils.createAmericanDate("2010-05-23T00:00:00");
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.util.Date newDate = new Date(sqlDate.getTime());
        Assertions.assertEquals(date, newDate);

        java.util.Date dateWithTime = UtilToSqlDateUtils.createAmericanDate("2010-05-23T23:59:59");
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(dateWithTime.getTime());
        java.util.Date newDateWithTime = new Date(sqlTimestamp.getTime());
        Assertions.assertEquals(dateWithTime, newDateWithTime);
    }

}
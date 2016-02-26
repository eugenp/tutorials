package com.baeldung.java8;
import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import org.junit.Test;

/**
 * This class shows the usage of the Java 8 date/time API
 */
public class Java8DateTimeTest {

    /**
     * Test the LocalDateTime usage
     */
    @Test
    public void testLocalDateTime() {
        // An instance of LocalDateTime from year, month, day, hour and minute.
        LocalDateTime localDateTime = LocalDateTime.of(1994, Month.APRIL, 15, 11, 30);
        assertEquals("1994-04-15T11:30", localDateTime.toString());
        // An instance of LocalDateTime from an Instant and zone ID.
        LocalDateTime timeWithZone = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        System.out.println("timeWithZone: " + timeWithZone);
    }

    /**
     * Test the LocalTime and LocalDate usage
     */
    @Test
    public void testDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current DateTime: " + currentDateTime);

        Month month = currentDateTime.getMonth();
        int day = currentDateTime.getDayOfMonth();
        int seconds = currentDateTime.getSecond();

        System.out.println("Month: " + month + "day: " + day + "seconds: " + seconds);

        LocalDate currentDate = currentDateTime.toLocalDate();
        System.out.println("Current Date: " + currentDate);

        LocalDateTime localDateTime = currentDateTime.withDayOfMonth(10).withYear(2012);
        System.out.println("localDateTime: " + localDateTime);

        // 12 december 2014
        LocalDate twelveDec2014 = LocalDate.of(2014, Month.DECEMBER, 12);
        assertEquals("2014-12-12", twelveDec2014.toString()); 
        
        // 22 hour 15 minutes
        LocalTime localTime = LocalTime.of(22, 15);
        assertEquals("22:15", localTime.toString()); 

        // Get the time based on given String.
        LocalTime time  = LocalTime.parse("10:15:30");
        assertEquals("10:15:30", time.toString());

        // 12:00
        LocalTime midday = LocalTime.of(12, 0); 
        assertEquals("12:00", midday.toString());
    }
    
    /**
     * Test the DayOfWeek usage
     */
    @Test
    public void testDayOfWeek() {
        LocalDate date = LocalDate.of(2016, Month.JANUARY, 15);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        assertEquals(DayOfWeek.FRIDAY, dayOfWeek);
    }

    /**
     * Test the YearMonth and MonthDay usage
     */
    @Test
    public void testYearMonth() {
        YearMonth date = YearMonth.of(2016, Month.JANUARY);
        LocalDate lastDateOfMonth = date.atEndOfMonth();
        assertEquals("2016-01-31", lastDateOfMonth.toString());
        YearMonth date2 = YearMonth.of(2014, Month.FEBRUARY);
        int daysInMonth = date2.lengthOfMonth();
        assertEquals(28, daysInMonth);
        MonthDay date = MonthDay.of(Month.FEBRUARY, 29);
        // --02-29
        System.out.println(date);
    }

    /**
     * Test the ZoneOffset,OffsetDateTime and ZonedDateTime usage
     */
    @Test
    public void testMonthDay() {
        
        LocalDate sixtyFifthDayOf2010 = LocalDate.ofYearDay(2015, 65);
        assertEquals("2015-03-06", sixtyFifthDayOf2010.toString());

        LocalDateTime dateTime = LocalDateTime.of(2015, Month.JUNE, 30, 1, 30);
        ZoneOffset offset = ZoneOffset.of("+03:00");
        OffsetDateTime plusThree = OffsetDateTime.of(dateTime, offset);
        // 2015-06-30T01:30+03:00
        System.out.println(plusThree);

        ZoneId asiaZone = ZoneId.of("Asia/Kolkata");
        ZonedDateTime asiaDateTime = ZonedDateTime.of(dateTime, asiaZone);
        // 2015-06-30T01:30+05:30[Asia/Kolkata]
        System.out.println(asiaDateTime);
    }

    /**
     * Test the Period usage
     */
    @Test
    public void testPeriod() {

        // 2012-05-10
        LocalDate firstDate = LocalDate.of(2012, 5, 10);
        // 2015-11-07
        LocalDate secondDate = LocalDate.of(2015, 11, 7);
        Period period = Period.between(firstDate, secondDate);

        int days = period.getDays(); // 28
        int months = period.getMonths(); // 5
        int years = period.getYears(); // 3

        assertEquals(28, days);
        assertEquals(5, months);
        assertEquals(3, years);

        Period oneMonthsAndFiveDays = Period.ofMonths(1).plusDays(5);
        LocalDate fourthOfMarch = LocalDate.of(2015, 3, 4);

        // add one months and five days to 2015-03-04, result is 2015-04-09
        LocalDate ninethOfApril = fourthOfMarch.plus(oneMonthsAndFiveDays);

        assertEquals("2015-04-09", ninethOfApril.toString());
    }

    /**
     * Test the Clock usage
     */
    @Test
    public void testClock() {
        // Returns the current time based on your system clock and set to UTC.
        Clock clock = Clock.systemUTC();
        System.out.println(clock);
        // Returns time based on system clock zone
        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println(defaultClock);
    }

    /**
     * Test the Parsing with DateTimeFormatter
     */
    @Test
    public void testParsing() {
        String input = "2015-08-01 11:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // 2015-08-01T11:30
        LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
        assertEquals("2015-08-01T11:30", dateTime.toString());
        System.out.println(dateTime);
        // 2014-01-20
        LocalDate fromCustomPattern = LocalDate.parse("20.01.2014", DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        assertEquals("2014-01-20", fromCustomPattern.toString());
        System.out.println(fromCustomPattern);
    }

    /**
     * Test the Formatting with DateTimeFormatter
     */
    @Test
    public void testFormatting() {
        LocalDateTime dateTime = LocalDateTime.of(2015, Month.MAY, 11, 10, 35);
        // format ISO date time 2015-05-11T10:35:00
        String asIsoDateTime = dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        assertEquals("2015-05-11T10:35:00", asIsoDateTime.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // "2015-05-11 10:35"
        String formattedDateTime = dateTime.format(formatter);
        assertEquals("2015-05-11 10:35", formattedDateTime.toString());

        // 11. mai 2015
        String frenchDate = dateTime.format(DateTimeFormatter.ofPattern("d. MMMM yyyy", new Locale("fr")));
        assertEquals("11. mai 2015", frenchDate);
    }

    /**
     * Test the TemporalAdjusters
     */
    @Test
    public void testTemporalAdjusters() {
        LocalDate date = LocalDate.of(2016, Month.JANUARY, 15);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        System.out.println(date + " is on a " + dayOfWeek);
        System.out.println("first day of Month: " + date.with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println("first Monday of Month: " + date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
        System.out.println("last day of Month:" + date.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("first day of next Month:" + date.with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println("first day of next Year: " + date.with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.println("first day of Year: " + date.with(TemporalAdjusters.firstDayOfYear()));
    }

}

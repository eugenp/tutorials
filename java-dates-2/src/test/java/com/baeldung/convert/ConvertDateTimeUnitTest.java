package com.baeldung.convert;

import org.junit.Test;

public class ConvertDateTimeUnitTest {

    @Test
    public void givenLocalDateTime_WhenGetMillis() {
        long millis = ConvertDateTime.java8LocalDateTime();
        System.out.println("LocalDateTime in milliseconds : " + millis);
    }

    @Test
    public void givenJava8Instant_WhenGetMillis() {
        long millis = ConvertDateTime.java8Instant();
        System.out.println("Java 8 Instant in milliseconds : " + millis);
    }

    @Test
    public void givenDate_WhenGetMillis() {
        long millis = ConvertDateTime.coreDate();
        System.out.println("Core Date in milliseconds : " + millis);
    }

    @Test
    public void givenCalendar_WhenGetMillis() {
        long millis = ConvertDateTime.calendar();
        System.out.println("Calendar in milliseconds : " + millis);
    }

    @Test
    public void givenJodaInstant_WhenGetMillis() {
        long millis = ConvertDateTime.jodaInstant();
        System.out.println("JODA Instant in milliseconds : " + millis);
    }

    @Test
    public void givenJODADateTime_WhenGetMillis() {
        long millis = ConvertDateTime.jodaDateTime();
        System.out.println("JODA DateTime in milliseconds : " + millis);
    }
}

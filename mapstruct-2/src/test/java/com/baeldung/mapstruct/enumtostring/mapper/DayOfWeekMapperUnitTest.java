package com.baeldung.mapstruct.enumtostring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;

import static org.junit.Assert.*;

public class DayOfWeekMapperUnitTest {
    private final DayOfWeekMapper dayOfWeekMapper = DayOfWeekMapper.INSTANCE;

    @ParameterizedTest
    @CsvSource(
            {"MONDAY,MONDAY", "TUESDAY,TUESDAY", "WEDNESDAY,WEDNESDAY", "THURSDAY,THURSDAY", "FRIDAY,FRIDAY", "SATURDAY,SATURDAY", "SUNDAY,SUNDAY"})
    void whenDayOfWeekMapped_thenGetsNameString(DayOfWeek source, String expected) {
        final String target = dayOfWeekMapper.toString(source);
        assertEquals(expected, target);
    }

    @Test
    void whenNullDayOfWeekMapped_thenGetsNullResult() {
        final String target = dayOfWeekMapper.toString(null);
        assertNull(target);
    }

    @Test
    void whenNullDayOfWeekMappedWithDefaults_thenReturnsDefault() {
        final String target = dayOfWeekMapper.toStringWithDefault(null);
        assertEquals("MONDAY", target);
    }

    @ParameterizedTest
    @CsvSource(
            {"MONDAY,MONDAY", "TUESDAY,TUESDAY", "WEDNESDAY,WEDNESDAY", "THURSDAY,THURSDAY", "FRIDAY,FRIDAY", "SATURDAY,SATURDAY", "SUNDAY,SUNDAY"})
    void whenNameStringMapped_thenGetsDayOfWeek(String source, DayOfWeek expected) {
        final DayOfWeek target = dayOfWeekMapper.nameStringToDayOfWeek(source);
        assertEquals(expected, target);
    }

    @Test
    void whenInvalidNameStringMapped_thenThrowsIllegalArgumentException() {
        final String source = "Mon";
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dayOfWeekMapper.nameStringToDayOfWeek(source);
        });
        assertTrue(exception.getMessage().equals("Unexpected enum constant: " + source));
    }

    @ParameterizedTest
    @CsvSource(
            {"MONDAY,Mon", "TUESDAY,Tue", "WEDNESDAY,Wed", "THURSDAY,Thu", "FRIDAY,Fri", "SATURDAY,Sat", "SUNDAY,Sun"})
    void whenDayOfWeekMapped_thenGetsShortString(DayOfWeek source, String expected) {
        final String target = dayOfWeekMapper.toShortString(source);
        assertEquals(expected, target);
    }

    @ParameterizedTest
    @CsvSource(
            {"Mon,MONDAY", "Tue,TUESDAY", "Wed,WEDNESDAY", "Thu,THURSDAY", "Fri,FRIDAY", "Sat,SATURDAY", "Sun,SUNDAY"})
    void whenShortStringMapped_thenGetsDayOfWeek(String source, DayOfWeek expected) {
        final DayOfWeek target = dayOfWeekMapper.shortStringToDayOfWeek(source);
        assertEquals(expected, target);
    }

    @ParameterizedTest
    @CsvSource({"Mon, MONDAY"})
    void whenInvalidNameStringMappedWithDefaults_thenReturnsDefault(String source, DayOfWeek expected) {
        final DayOfWeek target = dayOfWeekMapper.nameStringToDayOfWeekWithDefaults(source);
        assertEquals(expected, target);
    }
}

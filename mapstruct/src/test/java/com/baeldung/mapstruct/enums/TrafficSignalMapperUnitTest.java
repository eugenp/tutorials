package com.baeldung.mapstruct.enums;

import com.baeldung.mapstruct.enums.mapper.TrafficSignalMapper;
import com.baeldung.mapstruct.enums.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * The traffic signal mapper test.
 */
class TrafficSignalMapperUnitTest {

    /**
     * To traffic signal.
     */
    @Test
    void whenRoadSignIsMapped_thenGetTrafficSignal() {
        RoadSign source = RoadSign.Move;
        TrafficSignal target = TrafficSignalMapper.INSTANCE.toTrafficSignal(source);
        assertEquals(TrafficSignal.Go, target);
    }

    /**
     * String to traffic signal.
     */
    @Test
    void whenStringIsMapped_thenGetTrafficSignal() {
        String source = RoadSign.Move.name();
        TrafficSignal target = TrafficSignalMapper.INSTANCE.stringToTrafficSignal(source);
        assertEquals(TrafficSignal.Go, target);
    }

    /**
     * String to unmapped traffic signal.
     */
    @Test
    void whenStringIsUnmapped_thenGetException() {
        String source = "Proceed";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TrafficSignalMapper.INSTANCE.stringToTrafficSignal(source);
        });
        assertEquals("Unexpected enum constant: " + source, exception.getMessage());
    }

    /**
     * Traffic signal to string.
     */
    @Test
    void whenTrafficSignalIsMapped_thenGetString() {
        TrafficSignal source = TrafficSignal.Go;
        String targetTrafficSignalStr = TrafficSignalMapper.INSTANCE.trafficSignalToString(source);
        assertEquals("Go", targetTrafficSignalStr);
    }

    /**
     * Traffic signal to int.
     *
     * @param source   the traffic signal
     * @param expected the expected traffic signal number
     */
    @ParameterizedTest
    @CsvSource({"Off,0", "Stop,1"})
    void whenTrafficSignalIsMapped_thenGetInt(TrafficSignal source, int expected) {
        Integer targetTrafficSignalInt = TrafficSignalMapper.INSTANCE.convertTrafficSignalToInteger(source);
        TrafficSignalNumber targetTrafficSignalNumber = TrafficSignalMapper.INSTANCE.trafficSignalToTrafficSignalNumber(source);
        assertEquals(expected, targetTrafficSignalInt.intValue());
        assertEquals(expected, targetTrafficSignalNumber.getNumber().intValue());
    }

    /**
     * Traffic signal to simple traffic signal.
     *
     * @param source   the traffic signal
     * @param expected the expected traffic signal
     */
    @ParameterizedTest
    @CsvSource({"Off,Off", "Go,On", "Stop,Off"})
    void whenTrafficSignalIsMapped_thenGetSimpleTrafficSignal(TrafficSignal source, SimpleTrafficSignal expected) {
        SimpleTrafficSignal target = TrafficSignalMapper.INSTANCE.toSimpleTrafficSignal(source);
        assertEquals(expected, target);
    }

    /**
     * Traffic signal to working day.
     *
     * @param source   the traffic signal
     * @param expected the expected traffic signal
     */
    @ParameterizedTest
    @CsvSource({"Off,Off", "Go,On", "Stop,Off"})
    void whenTrafficSignalIsMappedWithRemaining_thenGetTrafficSignal(TrafficSignal source, SimpleTrafficSignal expected) {
        SimpleTrafficSignal targetTrafficSignal = TrafficSignalMapper.INSTANCE.toSimpleTrafficSignalWithRemaining(source);
        assertEquals(expected, targetTrafficSignal);
    }

    /**
     * Traffic signal to working day.
     *
     * @param source   the traffic signal
     * @param expected the expected traffic signal
     */
    @ParameterizedTest
    @CsvSource({"Off,Off", "Go,On", "Stop,Off"})
    void whenTrafficSignalIsMappedWithUnmapped_thenGetTrafficSignal(TrafficSignal source, SimpleTrafficSignal expected) {
        SimpleTrafficSignal target = TrafficSignalMapper.INSTANCE.toSimpleTrafficSignalWithUnmapped(source);
        assertEquals(expected, target);
    }

    /**
     * Traffic signal to simple traffic signal with null handling.
     *
     * @param source   the traffic signal
     * @param expected the expected traffic signal
     */
    @ParameterizedTest
    @CsvSource({",Off", "Go,On", "Stop,"})
    void whenTrafficSignalIsMappedWithNull_thenGetTrafficSignal(TrafficSignal source, SimpleTrafficSignal expected) {
        SimpleTrafficSignal targetTrafficSignal = TrafficSignalMapper.INSTANCE.toSimpleTrafficSignalWithNullHandling(source);
        assertEquals(expected, targetTrafficSignal);
    }

    /**
     * Traffic signal to traffic signal with exception handling.
     *
     * @param source   the traffic signal
     * @param expected the expected traffic signal
     */
    @ParameterizedTest
    @CsvSource({",", "Go,On", "Stop,"})
    void whenTrafficSignalIsMappedWithException_thenGetTrafficSignal(TrafficSignal source, SimpleTrafficSignal expected) {
        if (source == TrafficSignal.Go) {
            SimpleTrafficSignal targetTrafficSignal = TrafficSignalMapper.INSTANCE.toSimpleTrafficSignalWithExceptionHandling(source);
            assertEquals(expected, targetTrafficSignal);
        } else {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                TrafficSignalMapper.INSTANCE.toSimpleTrafficSignalWithExceptionHandling(source);
            });
            assertEquals("Unexpected enum constant: " + source, exception.getMessage());
        }
    }

    /**
     * Apply suffix.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"Off,Off_Value", "Go,Go_Value"})
    void whenTrafficSignalIsMappedWithSuffix_thenGetTrafficSignalSuffixed(TrafficSignal source, TrafficSignalSuffixed expected) {
        TrafficSignalSuffixed result = TrafficSignalMapper.INSTANCE.applySuffix(source);
        assertEquals(expected, result);
    }

    /**
     * Apply prefix.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"Off,Value_Off", "Go,Value_Go"})
    void whenTrafficSignalIsMappedWithPrefix_thenGetTrafficSignalPrefixed(TrafficSignal source, TrafficSignalPrefixed expected) {
        TrafficSignalPrefixed result = TrafficSignalMapper.INSTANCE.applyPrefix(source);
        assertEquals(expected, result);
    }

    /**
     * Strip suffix.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"Off_Value,Off", "Go_Value,Go"})
    void whenTrafficSignalSuffixedMappedWithStripped_thenGetTrafficSignal(TrafficSignalSuffixed source, TrafficSignal expected) {
        TrafficSignal result = TrafficSignalMapper.INSTANCE.stripSuffix(source);
        assertEquals(expected, result);
    }

    /**
     * Strip prefix.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"Value_Off,Off", "Value_Stop,Stop"})
    void whenTrafficSignalPrefixedMappedWithStripped_thenGetTrafficSignal(TrafficSignalPrefixed source, TrafficSignal expected) {
        TrafficSignal result = TrafficSignalMapper.INSTANCE.stripPrefix(source);
        assertEquals(expected, result);
    }

    /**
     * Apply lowercase.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"Off,off", "Go,go"})
    void whenTrafficSignalMappedWithLower_thenGetTrafficSignalLowercase(TrafficSignal source, TrafficSignalLowercase expected) {
        TrafficSignalLowercase result = TrafficSignalMapper.INSTANCE.applyLowercase(source);
        assertEquals(expected, result);
    }

    /**
     * Apply uppercase.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"Off,OFF", "Go,GO"})
    void whenTrafficSignalMappedWithUpper_thenGetTrafficSignalUppercase(TrafficSignal source, TrafficSignalUppercase expected) {
        TrafficSignalUppercase result = TrafficSignalMapper.INSTANCE.applyUppercase(source);
        assertEquals(expected, result);
    }

    /**
     * Underscore to capital.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"OFF_VALUE,Off_Value", "GO_VALUE,Go_Value"})
    void whenTrafficSignalUnderscoreMappedWithCapital_thenGetStringCapital(TrafficSignalUnderscore source, String expected) {
        String result = TrafficSignalMapper.INSTANCE.underscoreToCapital(source);
        assertEquals(expected, result);
    }

    /**
     * Lowercase to capital.
     *
     * @param source   the traffic signal
     * @param expected the expected result
     */
    @ParameterizedTest
    @CsvSource({"off,Off", "go,Go"})
    void whenTrafficSignalLowercaseMappedWithCapital_thenGetTrafficSignal(TrafficSignalLowercase source, TrafficSignal expected) {
        TrafficSignal result = TrafficSignalMapper.INSTANCE.lowercaseToCapital(source);
        assertEquals(expected, result);
    }
}
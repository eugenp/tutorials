package com.baeldung.restoreipaddress;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.stream.Stream;

public class RestoreIPAddressUnitTest {

    static Stream<Object> provideImplementations() {
        return Stream.of(
            new Iterative(),
            new DynamicProgramming(),
            new Backtracking()
        );
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenValidInput_whenUsingImplementation_thenRestoreIPAddress(Object implementation) {
        String input = "25525511135";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.contains("255.255.11.135"));
        assertTrue(result.contains("255.255.111.35"));
        assertEquals(2, result.size());
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenMinimumLength_whenUsingImplementation_thenRestoreIPAddress(Object implementation) {
        String input = "4567";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.contains("4.5.6.7"));
        assertEquals(1, result.size());
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenEmptyString_whenUsingImplementation_thenReturnEmptyList(Object implementation) {
        String input = "";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenLongString_whenUsingImplementation_thenReturnEmptyList(Object implementation) {
        String input = "123456789012345";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenShortString_whenUsingImplementation_thenReturnEmptyList(Object implementation) {
        String input = "456";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenInvalidParts_whenUsingImplementation_thenReturnEmptyList(Object implementation) {
        String input = "265265278294";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("provideImplementations")
    public void givenLeadingZeros_whenUsingImplementation_thenRestoreIPAddress(Object implementation) {
        String input = "010010";
        List<String> result = ((RestoreIPAddress) implementation).restoreIPAddresses(input);
        assertTrue(result.contains("0.10.0.10"));
        assertTrue(result.contains("0.100.1.0"));
        assertEquals(2, result.size());
    }
}
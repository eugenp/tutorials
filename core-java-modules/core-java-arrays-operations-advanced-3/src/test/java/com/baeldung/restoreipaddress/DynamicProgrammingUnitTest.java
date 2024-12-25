package com.baeldung.restoreipaddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DynamicProgrammingUnitTest {
    DynamicProgramming dynamicProgramming = new DynamicProgramming();

    @Test
    public void givenValidInput_whenUsingIterative_thenRestoreIPAddress() {
        String input = "25525511135";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.contains("255.255.11.135"));
        assertTrue(result.contains("255.255.111.35"));
        assertEquals(2, result.size());
    }

    @Test
    public void givenMinimumLength_whenUsingIterative_thenRestoreIPAddress() {
        String input = "4567";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.contains("4.5.6.7"));
        assertEquals(1, result.size());
    }

    @Test
    public void givenEmptyString_whenUsingIterative_thenReturnEmptyList() {
        String input = "";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenLongString_whenUsingIterative_thenReturnEmptyList() {
        String input = "123456789012345";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenShortString_whenUsingIterative_thenReturnEmptyList() {
        String input = "456";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenInvalidParts_whenUsingIterative_thenReturnEmptyList() {
        String input = "265265278294";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.isEmpty());
    }

    @Test
    public void givenLeadingZeros_whenUsingIterative_thenRestoreIPAddress() {
        String input = "010010";
        List<String> result = dynamicProgramming.restoreIPAddresses(input);
        assertTrue(result.contains("0.10.0.10"));
        assertTrue(result.contains("0.100.1.0"));
        assertEquals(2, result.size());
    }
}

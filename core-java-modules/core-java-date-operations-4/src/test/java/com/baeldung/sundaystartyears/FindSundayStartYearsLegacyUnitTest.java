package com.baeldung.sundaystartyears;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindSundayStartYearsLegacyUnitTest {

    @Test
    public void givenYearRange_whenCheckingStartDayLegacy_thenReturnYearsStartingOnSunday() {
        List<Integer> expected = List.of(2006, 2012, 2017, 2023);
        List<Integer> result = FindSundayStartYearsLegacy.getYearsStartingOnSunday(2000, 2025);
        assertEquals(expected, result);
    }
}
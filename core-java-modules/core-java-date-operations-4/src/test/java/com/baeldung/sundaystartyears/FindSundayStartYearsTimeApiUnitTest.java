package com.baeldung.sundaystartyears;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindSundayStartYearsTimeApiUnitTest {

    @Test
    public void givenYearRange_whenCheckingStartDayTimeApi_thenReturnYearsStartingOnSunday() {
        List<Integer> expected = List.of(2006, 2012, 2017, 2023);
        List<Integer> result = FindSundayStartYearsTimeApi.getYearsStartingOnSunday(2000, 2025);
        assertEquals(expected, result);
    }
}
package com.baeldung.exceptions;

import com.google.common.base.Throwables;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static com.baeldung.exceptions.RootCauseFinder.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the {@link RootCauseFinder}.
 */
public class RootCauseFinderTest {

    @Test
    public void givenWrongFormatDate_whenFindingRootCauseUsingJava_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge("010102");
        } catch (CalculationException ex) {
            assertTrue(findCauseUsingPlainJava(ex) instanceof DateTimeParseException);
        }
    }

    @Test
    public void givenOutOfRangeDate_whenFindingRootCauseUsingJava_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge("2020-04-04");
        } catch (CalculationException ex) {
            assertTrue(findCauseUsingPlainJava(ex) instanceof DateOutOfRangeException);
        }
    }

    @Test
    public void givenNullDate_whenFindingRootCauseUsingJava_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge(null);
        } catch (Exception ex) {
            assertTrue(findCauseUsingPlainJava(ex) instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenWrongFormatDate_whenFindingRootCauseUsingApacheCommons_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge("010102");
        } catch (CalculationException ex) {
            assertTrue(ExceptionUtils.getRootCause(ex) instanceof DateTimeParseException);
        }
    }

    @Test
    public void givenOutOfRangeDate_whenFindingRootCauseUsingApacheCommons_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge("2020-04-04");
        } catch (CalculationException ex) {
            assertTrue(ExceptionUtils.getRootCause(ex) instanceof DateOutOfRangeException);
        }
    }

    @Test
    public void givenWrongFormatDate_whenFindingRootCauseUsingGuava_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge("010102");
        } catch (CalculationException ex) {
            assertTrue(Throwables.getRootCause(ex) instanceof DateTimeParseException);
        }
    }

    @Test
    public void givenOutOfRangeDate_whenFindingRootCauseUsingGuava_thenRootCauseFound() {
        try {
            AgeCalculator.calculateAge("2020-04-04");
        } catch (CalculationException ex) {
            assertTrue(Throwables.getRootCause(ex) instanceof DateOutOfRangeException);
        }
    }

}

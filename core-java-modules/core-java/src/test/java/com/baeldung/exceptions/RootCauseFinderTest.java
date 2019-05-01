package com.baeldung.exceptions;

import com.google.common.base.Throwables;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import static com.baeldung.exceptions.RootCauseFinder.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the {@link RootCauseFinder}.
 */
public class RootCauseFinderTest {

    @Test
    public void givenBirthDate_whenCalculatingAge_thenAgeReturned() {
        try {
            int age = AgeCalculator.calculateAge("1990-01-01");
            Assertions.assertEquals(1990, LocalDate
              .now()
              .minus(age, ChronoUnit.YEARS)
              .getYear());
        } catch (CalculationException e) {
            Assertions.fail(e.getMessage());
        }
    }

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

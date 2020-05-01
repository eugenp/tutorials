package com.baeldung.exceptions.rootcausefinder;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Utility class to find root cause exceptions.
 */
public class RootCauseFinder {

    public static Throwable findCauseUsingPlainJava(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    /**
     *  Calculates the age of a person from a given date.
     */
    static class AgeCalculator {

        private AgeCalculator() {
        }

        public static int calculateAge(String birthDate) throws CalculationException {
            if (birthDate == null || birthDate.isEmpty()) {
                throw new IllegalArgumentException();
            }

            try {
                return Period
                  .between(parseDate(birthDate), LocalDate.now())
                  .getYears();
            } catch (DateParseException ex) {
                throw new CalculationException(ex);
            }
        }

        private static LocalDate parseDate(String birthDateAsString) throws DateParseException {

            LocalDate birthDate;
            try {
                birthDate = LocalDate.parse(birthDateAsString);
            } catch (DateTimeParseException ex) {
                throw new InvalidFormatException(birthDateAsString, ex);
            }

            if (birthDate.isAfter(LocalDate.now())) {
                throw new DateOutOfRangeException(birthDateAsString);
            }

            return birthDate;
        }

    }

    static class CalculationException extends Exception {

        CalculationException(DateParseException ex) {
            super(ex);
        }
    }

    static class DateParseException extends Exception {

        DateParseException(String input) {
            super(input);
        }

        DateParseException(String input, Throwable thr) {
            super(input, thr);
        }
    }

    static class InvalidFormatException extends DateParseException {

        InvalidFormatException(String input, Throwable thr) {
            super("Invalid date format: " + input, thr);
        }
    }

    static class DateOutOfRangeException extends DateParseException {

        DateOutOfRangeException(String date) {
            super("Date out of range: " + date);
        }

    }

}

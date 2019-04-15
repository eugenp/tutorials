package com.baeldung.exceptions;

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

    static class IntParser {

        private IntParser() {
        }

        public static int parse(String input) throws InvalidNumber {
            if (input == null || input.isEmpty()) {
                throw new IllegalArgumentException();
            }

            try {
                return new IntParser().stringToInt(input.trim());
            } catch (NaNException ex) {
                throw new InvalidNumber(input, ex);
            }
        }

        private int stringToInt(String numberAsString) throws NaNException {
            try {
                return Integer.valueOf(numberAsString);
            } catch (NumberFormatException ex) {
                throw new NaNException(numberAsString, ex);
            }
        }

    }

    static class InvalidNumber extends Exception {

        InvalidNumber(String input, Throwable thr) {
            super("Invalid input for a number: " + input, thr);
        }
    }

    static class NaNException extends Exception {

        NaNException(String number, Throwable thr) {
            super(number + "is not a number", thr);
        }

    }

}

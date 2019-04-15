package com.baeldung.exceptions;

import com.google.common.base.Throwables;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;

import static com.baeldung.exceptions.RootCauseFinder.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the {@link RootCauseFinder}.
 */
public class RootCauseFinderTest {

    @Test
    public void givenNestedException_whenFindingRootCauseUsingJava_thenRootCauseFound() {
        try {
            IntParser.parse("text");
        } catch (InvalidNumber ex) {
            assertTrue(findCauseUsingPlainJava(ex) instanceof NumberFormatException);
        }
    }

    @Test
    public void givenNonNestedException_whenFindingRootCauseUsingJava_thenRootCauseFound() {
        try {
            IntParser.parse(null);
        } catch (Exception ex) {
            assertTrue(findCauseUsingPlainJava(ex) instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenNestedException_whenFindingRootCauseUsingApacheCommons_thenRootCauseFound() {
        try {
            IntParser.parse("text");
        } catch (InvalidNumber ex) {
            assertTrue(ExceptionUtils.getRootCause(ex) instanceof NumberFormatException);
        }
    }

    @Test
    public void givenNestedException_whenFindingRootCauseUsingGuava_thenRootCauseFound() {
        try {
            IntParser.parse("text");
        } catch (InvalidNumber ex) {
            assertTrue(Throwables.getRootCause(ex) instanceof NumberFormatException);
        }
    }

}

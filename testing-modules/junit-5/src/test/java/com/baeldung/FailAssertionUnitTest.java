package com.baeldung;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class FailAssertionUnitTest {

    @Test
    void failThrowable() {
        try {
            safeMethod();
            // more testing code
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void failMessageThrowable() {
        try {
            safeMethod();
            // more testing code
        } catch (Exception e) {
            fail("Unexpected exception was thrown", e);
        }
    }

    @Test
    @Disabled
    void failMessageSupplier() {
        Supplier<String> timedMessage = () -> DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        fail(timedMessage);
    }

    @Test
    void genericType() {
        Stream.of().map(entry -> fail("should not be called"));
    }

    private void safeMethod() {
        return;
    }
}

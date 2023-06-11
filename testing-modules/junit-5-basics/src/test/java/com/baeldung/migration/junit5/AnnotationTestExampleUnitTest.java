package com.baeldung.migration.junit5;

import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("annotations")
@Tag("junit5")
class AnnotationTestExampleUnitTest {

    @Test
    void shouldRaiseAnException() {
        Assertions.assertThrows(Exception.class, () -> {
            throw new Exception("This is my expected exception");
        });
    }

    @Test
    @Disabled
    void shouldFailBecauseTimeout() {
        Assertions.assertTimeout(Duration.ofMillis(1), () -> Thread.sleep(10));
    }
}

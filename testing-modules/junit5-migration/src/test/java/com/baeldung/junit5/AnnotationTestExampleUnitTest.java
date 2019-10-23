package com.baeldung.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.time.Duration;

@Tag("annotations")
@Tag("junit5")
@RunWith(JUnitPlatform.class)
public class AnnotationTestExampleUnitTest {
    @Test
    public void shouldRaiseAnException() throws Exception {
        Assertions.assertThrows(Exception.class, () -> {
            throw new Exception("This is my expected exception");
        });
    }

    @Test
    @Disabled
    public void shouldFailBecauseTimeout() throws InterruptedException {
        Assertions.assertTimeout(Duration.ofMillis(1), () -> Thread.sleep(10));
    }
}

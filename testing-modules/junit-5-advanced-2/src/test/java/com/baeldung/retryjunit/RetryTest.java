package com.baeldung.retryjunit;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RetryExtension.class)
public class RetryTest {
    private static int attempt = 0;

    @Test
    public void testWithRetry() {
        attempt++;
        System.out.println("Test attempt: " + attempt);
        if (attempt < 3) {
            throw new RuntimeException("Failing test");
        }
    }
}

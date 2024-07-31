package com.baeldung.systemstubs;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakeDatabaseTestResourceUnitTest {
    @Nested
    class ExecuteAround {
        @Test
        void theResourceIsClosedToStartWith() throws Exception {
            FakeDatabaseTestResource fake = new FakeDatabaseTestResource();
            assertThat(fake.getDatabaseConnection()).isEqualTo("closed");

            fake.execute(() -> {
                assertThat(fake.getDatabaseConnection()).isEqualTo("open");
            });
        }
    }
}

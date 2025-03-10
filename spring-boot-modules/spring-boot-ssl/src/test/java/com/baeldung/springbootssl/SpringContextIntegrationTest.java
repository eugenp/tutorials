package com.baeldung.springbootssl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringContextIntegrationTest {

    @Test
    void shouldLoadSpringContextWithoutErrors() {
        // If the context fails to load, this test will fail.
        assertTrue(true, "The application context should load without errors.");
    }

}

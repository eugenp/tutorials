package com.baeldung.logging;

import com.baeldung.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = Application.class)
class GreetingServiceUnitTest {

    @Autowired
    private GreetingService greetingService;

    @Test
    void givenName_whenGreet_thenReturnCorrectResult() {
        String result = greetingService.greet("Baeldung");
        assertNotNull(result);
        assertEquals("Hello Baeldung", result);
    }
}
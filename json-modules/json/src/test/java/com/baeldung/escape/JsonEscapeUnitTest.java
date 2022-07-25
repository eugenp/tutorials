package com.baeldung.escape;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonEscapeUnitTest {

    private JsonEscape testedInstance;
    private static final String EXPECTED = "{\"message\":\"Hello \\\"World\\\"\"}";

    @BeforeEach
    void setUp() {
        testedInstance = new JsonEscape();
    }

    @Test
    void escapeJson() {
        String actual = testedInstance.escapeJson("Hello \"World\"");
        assertEquals(EXPECTED, actual);
    }

    @Test
    void escapeGson() {
        String actual = testedInstance.escapeGson("Hello \"World\"");
        assertEquals(EXPECTED, actual);
    }

    @Test
    void escapeJackson() throws JsonProcessingException {
        String actual = testedInstance.escapeJackson("Hello \"World\"");
        assertEquals(EXPECTED, actual);
    }
}
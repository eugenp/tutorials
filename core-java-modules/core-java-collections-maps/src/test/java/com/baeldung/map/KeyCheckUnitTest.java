package com.baeldung.map;

import org.junit.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class KeyCheckUnitTest {

    @Test
    public void whenKeyIsPresent_thenContainsKeyReturnsTrue() {
        Map<String, String> map = Collections.singletonMap("key", "value");

        assertTrue(map.containsKey("key"));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    public void whenKeyHasNullValue_thenGetStillWorks() {
        Map<String, String> map = Collections.singletonMap("nothing", null);

        assertTrue(map.containsKey("nothing"));
        assertNull(map.get("nothing"));
    }
}
package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicIgnoreJsonFilterUnitTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenWritingWithoutFilter_idIsPresent() throws JsonProcessingException {
        final UserFilter user = new UserFilter(1L, "John");

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAll());

        final String result = mapper.setFilterProvider(filterProvider).writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertTrue(result.contains("1"));
    }

    @Test
    void whenWritingWithFilter_idIsIgnored() throws JsonProcessingException {
        final UserFilter user = new UserFilter(1L, "John");

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));

        final String result = mapper.writer(filterProvider).writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertFalse(result.contains("1"));
    }

    @Test
    void whenReadingWithoutFilter_idIsPresent() throws JsonProcessingException {
        final String json = "{\"id\":1,\"name\":\"John\"}";

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAll());

        final UserFilter result = mapper.setFilterProvider(filterProvider).readValue(json, UserFilter.class);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }

    @Test
    void whenReadingWithFilter_idIsStillPresent() throws JsonProcessingException {
        final String json = "{\"id\":1,\"name\":\"John\"}";

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));

        final UserFilter result = mapper.setFilterProvider(filterProvider).readValue(json, UserFilter.class);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }
}

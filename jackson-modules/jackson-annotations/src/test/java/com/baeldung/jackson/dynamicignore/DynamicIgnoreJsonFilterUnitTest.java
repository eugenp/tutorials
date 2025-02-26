package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicIgnoreJsonFilterUnitTest {

    @Test
    void whenWritingWithoutFilter_idIsPresent() throws JsonProcessingException {
        UserFilter user = new UserFilter(1L, "John");

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAll());

        String result = new ObjectMapper().setFilterProvider(filterProvider)
            .writeValueAsString(user);

        assertThat(result).contains("John");
        assertThat(result).contains("1");
    }

    @Test
    void whenWritingWithFilter_idIsIgnored() throws JsonProcessingException {
        UserFilter user = new UserFilter(1L, "John");

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));

        String result = new ObjectMapper().writer(filterProvider)
            .writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertFalse(result.contains("1"));

        assertThat(result).contains("John");
        assertThat(result).doesNotContain("1");
    }

    @Test
    void whenReadingWithoutFilter_idIsPresent() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"John\"}";

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAll());

        UserFilter result = new ObjectMapper().setFilterProvider(filterProvider)
            .readValue(json, UserFilter.class);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }

    @Test
    void whenReadingWithFilter_idIsStillPresent() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"John\"}";

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));

        UserFilter result = new ObjectMapper().setFilterProvider(filterProvider)
            .readValue(json, UserFilter.class);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getName());
    }
}

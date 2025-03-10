package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicIgnoreJsonFilterUnitTest {

    @Test
    void whenWritingWithoutFilter_thenIdIsPresent() throws JsonProcessingException {
        UserWithFilter user = new UserWithFilter(1000L, "John");

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAll());

        ObjectMapper objectMapper = new ObjectMapper().setFilterProvider(filterProvider);
        String result = objectMapper.writeValueAsString(user);

        assertThat(result).contains("John");
        assertThat(result).contains("1000");
    }

    @Test
    void whenWritingWithFilter_thenIdIsIgnored() throws JsonProcessingException {
        UserWithFilter user = new UserWithFilter(1000L, "John");

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));

        ObjectMapper objectMapper = new ObjectMapper().setFilterProvider(filterProvider);
        String result = objectMapper.writeValueAsString(user);

        assertThat(result).contains("John");
        assertThat(result).doesNotContain("1000");
    }

    @Test
    void whenReadingWithoutFilter_thenIdIsPresent() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAll());

        ObjectMapper objectMapper = new ObjectMapper().setFilterProvider(filterProvider);
        UserWithFilter result = objectMapper.readValue(json, UserWithFilter.class);

        assertEquals(1000L, result.getId());
        assertEquals("John", result.getName());
    }

    @Test
    void whenReadingWithFilter_thenIdIsStillPresent() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("publicFilter", SimpleBeanPropertyFilter.serializeAllExcept("id"));

        ObjectMapper objectMapper = new ObjectMapper().setFilterProvider(filterProvider);
        UserWithFilter result = objectMapper.readValue(json, UserWithFilter.class);

        assertEquals(1000L, result.getId());
        assertEquals("John", result.getName());
    }
}

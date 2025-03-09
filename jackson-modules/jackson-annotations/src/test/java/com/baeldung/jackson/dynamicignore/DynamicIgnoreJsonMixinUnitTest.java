package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DynamicIgnoreJsonMixinUnitTest {

    @Test
    void whenWritingWithoutMixin_thenIdIsPresent() throws JsonProcessingException {
        UserMixin user = new UserMixin(1000L, "John");
        String result = new ObjectMapper().writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).contains("1000");
    }

    @Test
    void whenWritingWithPublicMixin_thenIdIsIgnored() throws JsonProcessingException {
        UserMixin user = new UserMixin(1000L, "John");
        ObjectMapper objectMapper = new ObjectMapper().addMixIn(UserMixin.class, UserMixin.PublicMixIn.class);
        String result = objectMapper.writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).doesNotContain("1000");
    }

    @Test
    void whenReadingWithoutMixin_thenIdIsPresent() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";
        UserMixin user = new ObjectMapper().readValue(json, UserMixin.class);
        assertEquals(1000L, user.getId());
        assertEquals("John", user.getName());
    }

    @Test
    void whenReadingWithPublicMixin_thenIdIsIgnored() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";
        ObjectMapper objectMapper = new ObjectMapper().addMixIn(UserMixin.class, UserMixin.PublicMixIn.class);
        UserMixin user = objectMapper.readValue(json, UserMixin.class);
        assertNull(user.getId());
        assertEquals("John", user.getName());
    }
}

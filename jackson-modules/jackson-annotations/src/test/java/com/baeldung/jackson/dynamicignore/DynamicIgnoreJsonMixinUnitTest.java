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
        UserWithMixin user = new UserWithMixin(1000L, "John");
        String result = new ObjectMapper().writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).contains("1000");
    }

    @Test
    void whenWritingWithPublicMixin_thenIdIsIgnored() throws JsonProcessingException {
        UserWithMixin user = new UserWithMixin(1000L, "John");
        ObjectMapper objectMapper = new ObjectMapper().addMixIn(UserWithMixin.class, UserWithMixin.PublicMixin.class);
        String result = objectMapper.writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).doesNotContain("1000");
    }

    @Test
    void whenReadingWithoutMixin_thenIdIsPresent() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";
        UserWithMixin user = new ObjectMapper().readValue(json, UserWithMixin.class);
        assertEquals(1000L, user.getId());
        assertEquals("John", user.getName());
    }

    @Test
    void whenReadingWithPublicMixin_thenIdIsIgnored() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";
        ObjectMapper objectMapper = new ObjectMapper().addMixIn(UserWithMixin.class, UserWithMixin.PublicMixin.class);
        UserWithMixin user = objectMapper.readValue(json, UserWithMixin.class);
        assertNull(user.getId());
        assertEquals("John", user.getName());
    }
}

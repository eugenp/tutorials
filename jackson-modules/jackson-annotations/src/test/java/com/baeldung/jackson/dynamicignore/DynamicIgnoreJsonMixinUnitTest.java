package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DynamicIgnoreJsonMixinUnitTest {

    @Test
    void whenWritingWithoutMixin_idIsPresent() throws JsonProcessingException {
        UserMixin user = new UserMixin(1L, "John");
        String result = new ObjectMapper().writeValueAsString(user);

        assertThat(result).contains("John");
        assertThat(result).contains("1");
    }

    @Test
    void whenWritingWithPublicMixin_idIsIgnored() throws JsonProcessingException {
        UserMixin user = new UserMixin(1L, "John");
        String result = new ObjectMapper().addMixIn(UserMixin.class, UserMixin.PublicMixIn.class)
            .writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).doesNotContain("1");
    }

    @Test
    void whenReadingWithoutMixin_idIsPresent() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"John\"}";
        UserMixin user = new ObjectMapper().readValue(json, UserMixin.class);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
    }

    @Test
    void whenReadingWithPublicMixin_idIsIgnored() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"John\"}";
        UserMixin user = new ObjectMapper().addMixIn(UserMixin.class, UserMixin.PublicMixIn.class)
            .readValue(json, UserMixin.class);
        assertNull(user.getId());
        assertEquals("John", user.getName());
    }
}

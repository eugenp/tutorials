package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicIgnoreJsonMixinUnitTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenWritingWithoutMixin_idIsPresent() throws JsonProcessingException {
        final UserMixin user = new UserMixin(1L, "John");
        final String result = mapper.writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertTrue(result.contains("1"));
    }

    @Test
    void whenWritingWithPublicMixin_idIsIgnored() throws JsonProcessingException {
        final UserMixin user = new UserMixin(1L, "John");
        final String result = mapper.addMixIn(UserMixin.class, UserMixin.PublicMixIn.class).writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertFalse(result.contains("1"));
    }

    @Test
    void whenReadingWithoutMixin_idIsPresent() throws JsonProcessingException {
        final String json = "{\"id\":1,\"name\":\"John\"}";
        final UserMixin user = mapper.readValue(json, UserMixin.class);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getName());
    }

    @Test
    void whenReadingWithPublicMixin_idIsIgnored() throws JsonProcessingException {
        final String json = "{\"id\":1,\"name\":\"John\"}";
        final UserMixin user = mapper.addMixIn(UserMixin.class, UserMixin.PublicMixIn.class).readValue(json, UserMixin.class);
        assertNull(user.getId());
        assertEquals("John", user.getName());
    }
}

package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicIgnoreJsonViewUnitTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenWritingWithPublicView_idIsIgnored() throws JsonProcessingException {
        final UserView user = new UserView(1L, "John");
        final String result = mapper.writerWithView(UserView.PublicView.class).writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertFalse(result.contains("1"));
    }

    @Test
    void whenWritingWithInternalView_idIsPresent() throws JsonProcessingException {
        final UserView user = new UserView(1L, "John");
        final String result = mapper.writerWithView(UserView.InternalView.class).writeValueAsString(user);
        assertTrue(result.contains("John"));
        assertTrue(result.contains("1"));
    }

    @Test
    void whenReadingWithPublicView_idIsIgnored() throws JsonProcessingException {
        final String json = "{\"id\":1,\"name\":\"John\"}";
        final UserView user = mapper.readerWithView(UserView.PublicView.class).forType(UserView.class).readValue(json);
        assertEquals("John", user.getName());
        assertNull(user.getId());
    }

    @Test
    void whenReadingWithInternalView_idIsPresent() throws JsonProcessingException {
        final String json = "{\"id\":1,\"name\":\"John\"}";
        final UserView user = mapper.readerWithView(UserView.InternalView.class).forType(UserView.class).readValue(json);
        assertEquals("John", user.getName());
        assertEquals(1L, user.getId());
    }

}

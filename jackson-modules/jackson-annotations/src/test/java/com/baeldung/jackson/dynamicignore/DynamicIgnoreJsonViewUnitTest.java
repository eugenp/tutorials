package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DynamicIgnoreJsonViewUnitTest {

    @Test
    void whenWritingWithPublicView_thenIdIsIgnored() throws JsonProcessingException {
        UserWithView user = new UserWithView(1000L, "John");
        ObjectWriter objectWriter = new ObjectMapper().writerWithView(UserWithView.PublicView.class);
        String result = objectWriter.writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).doesNotContain("1000");
    }

    @Test
    void whenWritingWithInternalView_thenIdIsPresent() throws JsonProcessingException {
        UserWithView user = new UserWithView(1000L, "John");
        ObjectWriter objectWriter = new ObjectMapper().writerWithView(UserWithView.InternalView.class);
        String result = objectWriter.writeValueAsString(user);
        assertThat(result).contains("John");
        assertThat(result).contains("1000");
    }

    @Test
    void whenReadingWithPublicView_thenIdIsIgnored() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";
        ObjectReader objectReader = new ObjectMapper().readerWithView(UserWithView.PublicView.class)
            .forType(UserWithView.class);
        UserWithView user = objectReader.readValue(json);
        assertEquals("John", user.getName());
        assertNull(user.getId());
    }

    @Test
    void whenReadingWithInternalView_thenIdIsPresent() throws JsonProcessingException {
        String json = "{\"id\":1000,\"name\":\"John\"}";
        ObjectReader objectReader = new ObjectMapper().readerWithView(UserWithView.InternalView.class)
            .forType(UserWithView.class);
        UserWithView user = objectReader.readValue(json);
        assertEquals("John", user.getName());
        assertEquals(1000L, user.getId());
    }

}

package com.baeldung.jackson.try1;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import com.baeldung.jackson.dtos.ItemWithSerializer;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDeserializationUnitTest {

    @Test
    public final void givenDeserializerIsOnClass_whenDeserializingCustomRepresentation_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":2}";

        final ItemWithSerializer readValue = new ObjectMapper().readValue(json, ItemWithSerializer.class);
        assertThat(readValue, notNullValue());
    }

}

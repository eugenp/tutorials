package com.baeldung.deserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MultipleWrapperDeserializerTest {

    @Test
    void whenDeserialisingItemWithMultipleWrappers_ThenAllWrappersAreCorrectlyParsed() throws JsonProcessingException {
        final String json = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":{\"id\":2,\"name\":\"theUser\"},\"count\":5}";

        SimpleModule module = new SimpleModule().addDeserializer(Wrapper.class, new WrapperDeserializer());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        final ItemWithMultipleWrappers readValue = objectMapper.readValue(json, ItemWithMultipleWrappers.class);

        assertEquals(2, readValue.getOwner().getValue().id);
        assertEquals("theUser", readValue.getOwner().getValue().name);
        assertEquals(5, readValue.getCount().getValue());
    }
}
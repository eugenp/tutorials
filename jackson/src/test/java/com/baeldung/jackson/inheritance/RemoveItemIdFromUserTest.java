package com.baeldung.jackson.inheritance;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RemoveItemIdFromUserTest {
    @Test
    public void givenRemoveItemJson_whenDeserialize_shouldHaveProperClassType() throws IOException {
        //given
        Event event = new RemoveItemIdFromUser(new Event.Metadata("1", 12345567L), "item_1", 2L);
        ObjectMapper objectMapper = new ObjectMapper();
        String eventJson = objectMapper.writeValueAsString(event);

        //when
        Event result = new ObjectMapper().readValue(eventJson, Event.class);

        //then
        assertTrue(result instanceof RemoveItemIdFromUser);
        assertEquals("item_1", ((RemoveItemIdFromUser) result).getItemId());
        new EventProcessor().process(result);
    }

}